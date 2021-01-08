package com.template.states;

import com.template.contracts.HighlyRegulatedContract;
import net.corda.core.contracts.BelongsToContract;
import net.corda.core.contracts.ContractState;
import net.corda.core.identity.AbstractParty;
import net.corda.core.identity.Party;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

@BelongsToContract(HighlyRegulatedContract.class)
public class HighlyRegulatedState implements ContractState {
    private final Party seller;
    private final Party buyer;


    public HighlyRegulatedState(Party seller, Party buyer) {
        this.seller = seller;
        this.buyer = buyer;
    }

    public Party getSeller() {
        return seller;
    }

    public Party getBuyer() {
        return buyer;
    }


    @NotNull
    @Override
    public List<AbstractParty> getParticipants() {
        return Arrays.asList(buyer,seller);
    }
}
