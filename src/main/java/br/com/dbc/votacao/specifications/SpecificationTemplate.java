package br.com.dbc.votacao.specifications;

import br.com.dbc.votacao.models.Pauta;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {

    @And({
            @Spec(path = "descricao", spec = Like.class)
    })
    public interface PautaSpec extends Specification<Pauta> {}
}