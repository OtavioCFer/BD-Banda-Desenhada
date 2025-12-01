package br.banda_desenhada.controller;

import br.banda_desenhada.model.Questao;
import br.banda_desenhada.model.Opcao;
import br.banda_desenhada.repository.QuestaoRepository;
import br.banda_desenhada.repository.OpcaoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.Collectors; // <-- CORREÇÃO: Import necessário para resolver o erro

@Controller
@RequestMapping("/questoes")
public class QuestaoController {

    private final QuestaoRepository questaoRepository;
    private final OpcaoRepository opcaoRepository;

    public QuestaoController(QuestaoRepository questaoRepository, OpcaoRepository opcaoRepository) {
        this.questaoRepository = questaoRepository;
        this.opcaoRepository = opcaoRepository;
    }

    @GetMapping
    public String listarQuestoes(Model model) {
        List<Questao> questoes = questaoRepository.listarTodas();
        model.addAttribute("questoes", questoes);
        return "questoes/lista";
    }

    @GetMapping("/nova")
    public String novaQuestaoForm(Model model) {
        // Inicializa uma lista de 4 opções vazias para o formulário
        Questao questao = new Questao();
        questao.setOpcoes(criarOpcoesPadrao());
        
        model.addAttribute("questao", questao);
        return "questoes/form";
    }

    // SALVAMENTO NOVO
    @PostMapping
    public String salvarNovaQuestao(@ModelAttribute Questao questao,
                                    @RequestParam(required = false) String opcaoA,
                                    @RequestParam(required = false) String opcaoB,
                                    @RequestParam(required = false) String opcaoC,
                                    @RequestParam(required = false) String opcaoD) {

        // 1. INSERE a Questão e CAPTURA o ID gerado pelo banco.
        questaoRepository.inserir(questao); 
        
        // 2. Salva as Opções usando o ID recém-gerado.
        if ("MULTIPLA".equalsIgnoreCase(questao.getTipo())) {
             salvarOpcoes(questao.getIdQuestao(), opcaoA, opcaoB, opcaoC, opcaoD);
        }

        return "redirect:/questoes";
    }

    @GetMapping("/{idQuestao}/editar")
    public String editarQuestaoForm(@PathVariable Long idQuestao, Model model) {
        Questao questao = questaoRepository.buscarPorId(idQuestao);
        
        if ("MULTIPLA".equalsIgnoreCase(questao.getTipo())) {
             // Carrega as opções existentes e as completa com vazias (para o formulário)
             List<Opcao> existentes = opcaoRepository.listarPorQuestao(idQuestao);
             questao.setOpcoes(completarOpcoesPadrao(existentes));
        }
        
        model.addAttribute("questao", questao);
        return "questoes/form";
    }

    // ATUALIZAÇÃO
    @PostMapping("/{idQuestao}/atualizar")
    public String atualizarQuestao(@PathVariable Long idQuestao, 
                                   @ModelAttribute Questao questao,
                                   @RequestParam(required = false) String opcaoA,
                                   @RequestParam(required = false) String opcaoB,
                                   @RequestParam(required = false) String opcaoC,
                                   @RequestParam(required = false) String opcaoD) {
                                   
        questao.setIdQuestao(idQuestao);
        questaoRepository.atualizar(questao);
        
        // 1. LIMPA as Opções Antigas
        opcaoRepository.excluirPorQuestao(idQuestao);
        
        // 2. Salva as Novas Opções
        if ("MULTIPLA".equalsIgnoreCase(questao.getTipo())) {
             salvarOpcoes(idQuestao, opcaoA, opcaoB, opcaoC, opcaoD); 
        }
        
        return "redirect:/questoes";
    }

    @PostMapping("/{idQuestao}/remover")
    public String removerQuestao(@PathVariable Long idQuestao) {
        // Exclui as opções antes da questão (necessário devido à Foreign Key)
        opcaoRepository.excluirPorQuestao(idQuestao);
        questaoRepository.excluir(idQuestao);
        return "redirect:/questoes";
    }
    
    // ====================================
    // MÉTODOS AUXILIARES
    // ====================================
    
    private void salvarOpcoes(Long idQuestao, String opcaoA, String opcaoB, String opcaoC, String opcaoD) {
        List<String> letras = Arrays.asList("A", "B", "C", "D");
        List<String> textos = Arrays.asList(opcaoA, opcaoB, opcaoC, opcaoD);

        for (int i = 0; i < letras.size(); i++) {
            String texto = textos.get(i);
            if (texto != null && !texto.isBlank()) {
                Opcao opcao = new Opcao();
                opcao.setIdQuestao(idQuestao);
                opcao.setLetraOpcao(letras.get(i));
                opcao.setTextoOpcao(texto);
                opcaoRepository.inserir(opcao);
            }
        }
    }
    
    private List<Opcao> criarOpcoesPadrao() {
        List<Opcao> opcoes = new ArrayList<>();
        String[] letras = {"A", "B", "C", "D"};
        for (String letra : letras) {
            Opcao o = new Opcao();
            o.setLetraOpcao(letra);
            opcoes.add(o);
        }
        return opcoes;
    }
    
    private List<Opcao> completarOpcoesPadrao(List<Opcao> existentes) {
        List<Opcao> padrao = criarOpcoesPadrao();
        
        // CORREÇÃO AQUI (Onde estava o erro de compilação)
        java.util.Map<String, Opcao> existentesMap = existentes.stream()
            .collect(Collectors.toMap(Opcao::getLetraOpcao, o -> o));
            
        List<Opcao> resultado = new ArrayList<>();
        
        for (Opcao o : padrao) {
            if (existentesMap.containsKey(o.getLetraOpcao())) {
                resultado.add(existentesMap.get(o.getLetraOpcao()));
            } else {
                resultado.add(o);
            }
        }
        return resultado;
    }
}