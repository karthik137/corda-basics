package com.template.contracts;

import com.template.states.YoState;
import net.corda.core.contracts.CommandData;
import net.corda.core.contracts.CommandWithParties;
import net.corda.core.contracts.Contract;
import net.corda.core.transactions.LedgerTransaction;
import org.jetbrains.annotations.NotNull;
import static net.corda.core.contracts.ContractsDSL.requireSingleCommand;
import static net.corda.core.contracts.ContractsDSL.requireThat;

public class YoContract implements Contract {

    public static final String ID = "com.template.contracts.YoContract";


    @Override
    public void verify(@NotNull LedgerTransaction tx) throws IllegalArgumentException {

        final CommandWithParties<Commands> command = requireSingleCommand(tx.getCommands(), Commands.class);
        final Commands commandData = command.getValue();

        //If Command is of type send
        if(commandData.equals(new Commands.Send())){

            requireThat(req -> {
                //there can be no inputs when sending yo to other party
                req.using("There can be no inputs when yoing other parties", tx.getInputs().isEmpty());
                req.using("There must be one output",tx.getOutputs().size() == 1);
                YoState yoState = (YoState) tx.getOutput(0);

                req.using("No sending yo to yourself", !yoState.getOrigin().equals(yoState.getTarget()));
                req.using("The Yo must be signed by the sender", command.getSigners().contains(yoState.getOrigin().getOwningKey()));
                return null;
            });
        }
    }

    public interface Commands extends CommandData{

        //In our yo cordapp we only have send command
        class Send implements Commands{

        }
    }
}
