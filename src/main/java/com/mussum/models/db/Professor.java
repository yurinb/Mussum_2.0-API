package com.mussum.models.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Professor extends Usuario {

    private String nome;

    private String sobrenome;

    private String email;

    @Column(columnDefinition = "TEXT")
    private String sobre;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(columnDefinition = "TEXT")
    private String resumo;
    
    @Column(columnDefinition = "TEXT")
    private String formacao;

    @Column(columnDefinition = "TEXT")
    private String fotolink = "http://franquia.globalmedclinica.com.br/wp-content/uploads/2016/01/investidores-img-02-01.png";
    
    public String getResumo() {
	return resumo;
    }

    public void setResumo(String resumo) {
	this.resumo = resumo;
    }

    public String getFormacao() {
	return formacao;
    }

    public void setFormacao(String formacao) {
	this.formacao = formacao;
    }
    

    @OneToMany(mappedBy = "professor", cascade = CascadeType.REMOVE)
    private Set<Recado> recados;

    public String getFotolink() {
        return fotolink;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setFotolink(String fotolink) {
        this.fotolink = fotolink;
    }

    @JsonIgnore
    public Set<Recado> getRecados() {
        return recados;
    }

    public void setRecados(Set<Recado> recados) {
        this.recados = recados;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotolink = fotoUrl;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSobre() {
        return sobre;
    }

    public void setSobre(String sobre) {
        this.sobre = sobre;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    @Override
    public int hashCode() {
	int hash = 3;
	hash = 43 * hash + Objects.hashCode(this.nome);
	hash = 43 * hash + Objects.hashCode(this.sobrenome);
	hash = 43 * hash + Objects.hashCode(this.email);
	hash = 43 * hash + Objects.hashCode(this.sobre);
	hash = 43 * hash + Objects.hashCode(this.descricao);
	hash = 43 * hash + Objects.hashCode(this.fotolink);
	hash = 43 * hash + Objects.hashCode(this.recados);
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final Professor other = (Professor) obj;
	if (!Objects.equals(this.nome, other.nome)) {
	    return false;
	}
	if (!Objects.equals(this.sobrenome, other.sobrenome)) {
	    return false;
	}
	if (!Objects.equals(this.email, other.email)) {
	    return false;
	}
	if (!Objects.equals(this.sobre, other.sobre)) {
	    return false;
	}
	if (!Objects.equals(this.descricao, other.descricao)) {
	    return false;
	}
	if (!Objects.equals(this.fotolink, other.fotolink)) {
	    return false;
	}
	if (!Objects.equals(this.recados, other.recados)) {
	    return false;
	}
	return true;
    }
    
    

}
