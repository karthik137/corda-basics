package com.template.contracts;

import com.template.states.HighlyRegulatedState;
import net.corda.core.contracts.*;
import net.corda.core.transactions.LedgerTransaction;
import org.jetbrains.annotations.NotNull;

public class HighlyRegulatedContract implements Contract {
    public static final String ID = "com.template.contracts.HighlyRegulatedContract";

    @Override
    public void verify(@NotNull LedgerTransaction tx) throws IllegalArgumentException {
        //Trade command
        final CommandWithParties<Commands.Trade> command = ContractsDSL.requireSingleCommand(tx.getCommands(), Commands.Trade.class);

        //Check buyer and seller cannot be same entity
        HighlyRegulatedState highlyRegulatedState = (HighlyRegulatedState) tx.getOutput(0);

        if(highlyRegulatedState.getBuyer().equals(highlyRegulatedState.getSeller())){
            throw new IllegalArgumentException("Buyer and seller cannot be same");
        }

    }

    public interface Commands extends CommandData {
        class Trade implements  Commands{}
    }
}
