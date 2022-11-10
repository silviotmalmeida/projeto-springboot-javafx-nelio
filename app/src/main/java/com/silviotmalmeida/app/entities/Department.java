package com.silviotmalmeida.app.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

// classe que representa uma entidade Department
// é serializable para permitir operações de IO
// foi incluída a anotação de identificação como entidade para o JPA
// foi incluída a anotação para mapeamento da tabela tb_department
@Entity
@Table(name = "tb_department")
public class Department implements Serializable {

    // atributo serial (obrigatório em serializables)
    private static final long serialVersionUID = 1L;

    // declaração dos atributos
    //// definindo o id como chave primária autoincrementável
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // associação 1xn com a entidade Seller
    // definindo o nome do atributo do objeto Seller a ser considerado na associação
    // a anotação JsonIgnore serve informar que esta entidade não irá apresentar os
    // dados desta associação para evitar loop infinito na resposta e deve ser
    // colocado em um dos lados das associações
    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private List<Seller> sellers = new ArrayList<>();

    // construtor vazio (necessário para o framework)
    public Department() {
    }

    // construtor
    public Department(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // início dos getters e setters
    // ------------------------------------------------------------------
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Seller> getSellers() {
        return sellers;
    }
    // fim dos getters e setters
    // ------------------------------------------------------------------

    // hashcode e equals para permitir comparação de objetos
    // ------------------------------------------------------------------
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Department other = (Department) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    // toString para definir a impressão do objeto
    // ------------------------------------------------------------------
    @Override
    public String toString() {
        return "Department [id=" + id + ", name=" + name + "]";
    }
}
