package com.template.states;

import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class YoState implements ContractState {


    private Party origin;
    private Party target;
    private String yo;

    public YoState(Party origin, Party target, String yo) {
        this.origin = origin;
        this.target = target;
        this.yo = yo;
    }

    public Party getOrigin() {
        return origin;
    }

    public Party getTarget() {
        return target;
    }

    public String getYo() {
        return yo;
    }

    @Override
    public String toString() {
        return "YoState{" +
                "origin=" + origin +
                ", target=" + target +
                ", yo='" + yo + '\'' +
                '}';
    }

    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return Arrays.asList(origin,target);
    }
}
