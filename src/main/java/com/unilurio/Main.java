/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unilurio;

import com.unilurio.agents.SigletonNtxuvaBoard;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class Main {

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {

            // This is the important method. This launches the jade platform.
            jade.core.Runtime rt = jade.core.Runtime.instance();

            Profile profile = new ProfileImpl();
            // With the Profile you can set some options for the container
            profile.setParameter(Profile.PLATFORM_ID, "localhost");
            profile.setParameter(Profile.CONTAINER_NAME, "Main");

            // Create the Main Container
            jade.wrapper.AgentContainer mainContainer = rt.createMainContainer(profile);

            try {

                // Here I create an agent in the main container and start it.
                Object[] argsx = {"x"};
                AgentController agentx = mainContainer.createNewAgent("agentx", com.unilurio.agents.AgentAlphaBetaPruning.class.getName(), argsx);
//                AgentController agentx = mainContainer.createNewAgent("agentx", com.unilurio.agents.AgentMiniMax.class.getName(), argsx);
                agentx.start();
                Object[] argso = {"o"};

//                AgentController agento = mainContainer.createNewAgent("agento", com.unilurio.agents.AgentMiniMax.class.getName(), argso);
                 AgentController agento = mainContainer.createNewAgent("agento", com.unilurio.agents.AgentRadom.class.getName(), argso);
                agento.start();

            } catch (StaleProxyException e) {
                e.printStackTrace();
            }

            try {
                SigletonNtxuvaBoard.uniqueInstance = null;
                mainContainer.kill();
                rt.shutDown();
            } catch (StaleProxyException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}
