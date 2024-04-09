package br.com.zup.edu.nossosistemadebares.bar.request;

import br.com.zup.edu.nossosistemadebares.bar.Mesa;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MesaRequest {
    @NotNull
    @Min(2)
    private Integer capacidade;

    public MesaRequest(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public MesaRequest() {
    }

    public Mesa paraMesa(){
        return new Mesa(capacidade);
    }

    public Integer getCapacidade() {
        return capacidade;
    }
}
