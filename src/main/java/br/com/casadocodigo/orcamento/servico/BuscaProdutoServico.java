package br.com.casadocodigo.orcamento.servico;

import br.com.casadocodigo.orcamento.modelo.Produto;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jaumzera
 */
@Stateless
public class BuscaProdutoServico {

    private static final Logger LOG = Logger.getLogger(BuscaProdutoServico.class.getName());
    
    @PersistenceContext
    private EntityManager em;
    
    public List<Produto> buscar(String busca) {
        LOG.info("Buscando: " + busca);
        busca = ("%" + busca.replaceAll("\\s", "%") + "%").toLowerCase();
        return em.createQuery("SELECT p FROM Produto p WHERE LOWER(p.descricao) LIKE :_busca ORDER BY p.descricao")
                .setParameter("_busca", busca)
                .setMaxResults(10)
                .getResultList();
    } 
    
}
