package br.com.casadocodigo.orcamento.servico;

import br.com.casadocodigo.orcamento.modelo.Item;
import br.com.casadocodigo.orcamento.modelo.Orcamento;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * 
 * @author jaumzera
 */
@Stateful
public class OrcamentoServico {

    private static final Logger LOG = Logger.getLogger(OrcamentoServico.class.getName());
    
    @PersistenceContext 
    private EntityManager em;
    
    private Orcamento orcamento;
    
    @PostConstruct
    public void init() {
        LOG.info("Criando um novo orçamento");
        orcamento = new Orcamento();
    }
    
    public void adicionar(Item item) {
        LOG.info("Adicionando o item " + item);
        item.setOrcamento(orcamento);
        orcamento.getItens().add(item);
    }
    
    public void remover(Item item) {
        LOG.info("Removendo o item " + item);
        orcamento.getItens().remove(item);
    }
    
    public Orcamento getOrcamento() {
        return orcamento;
    }
    
    @Remove
    public Orcamento salvar() {
        LOG.info("Gravando no banco de dados");
        em.persist(orcamento);
        return orcamento;
    }
    
    @PreDestroy
    public void end() {
        LOG.info("Encerrando o orçamento");
        orcamento = null;
    }
}
