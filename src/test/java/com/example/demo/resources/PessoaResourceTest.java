package com.example.demo.resources;

import com.example.demo.DemoApplicationTests;
import com.example.demo.modelo.Endereco;
import com.example.demo.modelo.Pessoa;
import com.example.demo.modelo.Telefone;
import com.example.demo.repository.filtro.PessoaFiltro;
import io.restassured.http.ContentType;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

public class PessoaResourceTest extends DemoApplicationTests {

    @Test
    public void deve_procurar_pessoa_pelo_ddd_e_numero() {
        given()
                .pathParam("ddd", "86")
                .pathParam("numero", "35006330")
            .get("/pessoas/{ddd}/{numero}")
            .then()
                .log().body().and()
                .statusCode(HttpStatus.OK.value())
                .and()
                .body("codigo", equalTo(3),
                        "nome", equalTo("Cauê"),
                        "cpf", equalTo("38767897100"));
    }

    @Test
    public void deve_retornar_erro_nao_encontrado_quando_buscar_pessoa_por_telefone_inexistente() {
        given()
                .pathParam("ddd", "99").
                pathParam("numero", "987654321")
                .get("/pessoas/{ddd}/{numero}")
                .then()
                    .log().body().and()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("erro", equalTo("Não existe pessoa com o telefone (99)987654321"));
    }

    @Test
    public void deve_salvar_nova_pessoa_no_sistema() {
        final Pessoa pessoa = Pessoa.builder()
                .nome("Ricardo Enrico Emanuel Nunes")
                .cpf("02506673806").build();
        final Telefone telefone = Telefone.builder()
                .ddd("11")
                .numero("26375115").build();
        pessoa.setTelefones(Arrays.asList(telefone));

        final Endereco endereco = Endereco.builder()
                .logradouro("Rua Cento e Vinte e Três")
                .numero(538)
                .bairro("Cidade Miguel Badra")
                .cidade("Suzano").estado("SP").build();
        pessoa.setEnderecos(Arrays.asList(endereco));

        given()
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-type", ContentType.JSON)
                .body(pessoa)
            .when()
            .post("/pessoas")
            .then()
                .log().headers()
                .and()
                .log().body()
                .and()
                .statusCode(HttpStatus.CREATED.value())
                .header("Location", equalTo("http://localhost:" + porta + "/pessoas/6"))
                .body("codigo", equalTo(6),
                        "nome", equalTo("Ricardo Enrico Emanuel Nunes"),
                        "cpf", equalTo("02506673806"));
    }

    @Test
    public void nao_deve_salvar_duas_pessoas_com_o_mesmo_cpf() {
        final Pessoa pessoa = Pessoa.builder()
                .nome("Ricardo Enrico Emanuel Nunes")
                .cpf("72788740417").build();
        final Telefone telefone = Telefone.builder()
                .ddd("11")
                .numero("26375115").build();
        pessoa.setTelefones(Arrays.asList(telefone));

        given()
                .request()
                    .header("Accept", ContentType.ANY)
                    .header("Content-type", ContentType.JSON)
                    .body(pessoa)
                .when()
                .post("/pessoas")
            .then()
                    .log().body()
                .and()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("erro", equalTo("Já existe pessoa cadastrada com CPF '72788740417'"));
    }

    @Test
    public void deve_filtrar_pessoas_pelo_nome() {
        final PessoaFiltro filtro = new PessoaFiltro();
        filtro.setNome("a");

        given()
                .request()
                .header("Accept", ContentType.ANY)
                .header("Content-type", ContentType.JSON)
                .body(filtro)
            .when()
            .post("/pessoas/filtrar")
            .then()
                    .log().body()
                .and()
                    .statusCode(HttpStatus.OK.value())
                    .body("codigo", containsInAnyOrder(1,3,5),
                            "nome", containsInAnyOrder("Thiago", "Iago", "Cauê"),
                            "cpf", containsInAnyOrder("86730543540","72788740417","38767897100"));
    }
}
