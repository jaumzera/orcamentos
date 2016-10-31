package br.com.casadocodigo.orcamento.mb;

import br.com.casadocodigo.orcamento.modelo.Item;
import br.com.casadocodigo.orcamento.modelo.Orcamento;
import br.com.casadocodigo.orcamento.modelo.Produto;
import br.com.casadocodigo.orcamento.servico.BuscaProdutoServico;
import br.com.casadocodigo.orcamento.servico.OrcamentoServico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jaumzera
 */
@ViewScoped
@ManagedBean
public class OrcamentoMB implements Serializable {

    private static final Logger LOG = Logger.getLogger(OrcamentoMB.class.getName());

    private static final String ORCAMENTO_SK = "orcamentoServico";

    @EJB
    private BuscaProdutoServico buscaProdutoServico;

    private List<Produto> produtos;

    @NotNull(message = "Informe a palavra para a busca")
    private String busca;

    @NotNull(message = "Informe a quantidade")
    @Min(value = 1, message = "Mínimo é 1")
    @Max(value = 999, message = "Máximo é 999")
    private Integer quantidade = 1;

    @PostConstruct
    private void init() {
        LOG.info("Criando um OrcamentoMB");
    }

    public OrcamentoServico getOrcamentoServico() {
        Map<String, Object> session = FacesContext.getCurrentInstance()
                .getExternalContext().getSessionMap();

        if (!session.containsKey(ORCAMENTO_SK)) {
            try {
                InitialContext ic = new InitialContext();
                OrcamentoServico instance = (OrcamentoServico) 
                        ic.lookup("java:global/Orcamentos/OrcamentoServico");
                session.put(ORCAMENTO_SK, instance);
            } catch (NamingException ex) {
                LOG.severe(ex.getMessage());
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Erro ao obter serviço de orçamentos"));
            }
        }

        return (OrcamentoServico) session.get(ORCAMENTO_SK);
    }

    public List<Item> getItens() {
        return new ArrayList<Item>(
                getOrcamentoServico().getOrcamento().getItens());
    }

    public void buscar() {
        produtos = buscaProdutoServico.buscar(busca);
    }

    public void adicionar(Produto produto) {
        Item item = new Item();
        item.setProduto(produto);
        item.setQuantidade(quantidade);
        getOrcamentoServico().adicionar(item);
        quantidade = 1;
    }
    
    public void remover(Item item) {
        getOrcamentoServico().remover(item);
    }
    
    public String criar() {
        Orcamento orcamento = getOrcamentoServico().salvar();
        FacesContext.getCurrentInstance().getExternalContext()
                .getFlash().put("orcamento", orcamento);
        return "/exibirOrcamento.xhtml";
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getBusca() {
        return busca;
    }

    public void setBusca(String busca) {
        this.busca = busca;
    }
}
