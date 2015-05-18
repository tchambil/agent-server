package dcc.agent.server.service.siebog.core.Agents;

/**
 * Created by teo on 30/04/15.
 */


import dcc.agent.server.service.siebog.core.Behaviours.RTBehaviours;
//  import javax.realtime.*;

public class RTAgent extends Agent {

    public class RTThread {

        RTBehaviours behaviour;

       /*
        public RTThread(RTBehaviours)//,MemoryArea area)

        {
            super();
            this.behaviour=b;
            if (platform.Debug) System.out.println("EJECUTADO CONSTRUCTOR DEL HILO DE TIEMPO REAL DEL AGENTE ");
        }

        public void run()
        {
            long duracion,ahora,inicio;
            if (platform.Debug) System.out.println("EJECUTADO METODO RUN DEL HILO DE TIEMPO REAL DEL AGENTE");
            //inicio   = ahora = System.currentTimeMillis();
            if (platform.Kiwi)
            {
                platform.kiwi.tarea_on();
                System.out.println("numero tareas: "+platform.kiwi.num_tareas_on());
            }
            while (state_agent == 1)
            {
                behaviour.Task();
                waitForNextPeriod(); //esperamos al siguiente periodo
                ahora = System.currentTimeMillis();
            }

            if (platform.Kiwi)
            {
                platform.kiwi.tarea_off();
                System.out.println("numero tareas: "+platform.kiwi.num_tareas_on());
                if (platform.kiwi.num_tareas_on() == 0)
                {
                    platform.kiwi.volcar_resultados();
                }
            }
        }//fin run
    } //fin clase RTThread

    public RTAgent()
    {
    }

    public RTAgent(Platform platform,String name) {
        super(platform,name);
    }

    public boolean AddRTBehaviour(PeriodicBehaviours b)
    {
        RelativeTime start,cost,deadline,period;
        int priority;
        boolean feasibility;

        if (platform.Debug)	System.out.println("DENTRO DEL METODO ADDRTBEHAVIOUR DE LA CLASE RTAGENT");

        start = b.GetStart();
        cost = b.GetCost();
        deadline = b.GetDeadline();
        priority = b.GetPriority();
        period = b.GetPeriod();
        feasibility=false;

        SchedulingParameters scheduling = new PriorityParameters(priority);
        ReleaseParameters release = new PeriodicParameters(start,period,cost,deadline,null,null);
        RealtimeThread rt= new RTThread(b,scheduling,release);
        if (platform.Debug) System.out.println(">>>>>> Creado hilo de tiempo real.");
        if (b.GetFactibility())
        {
            feasibility = rt.addToFeasibility(); // si forzamos
        }
        else
        {
            feasibility = rt.addIfFeasible(); // si no forzamos
        }

        if (platform.Debug) System.out.println(">>>>>> Es factible: "+feasibility);

        feasibility = true;
        if (feasibility)
        {
            try
            {
                rt.start();
                if (platform.Debug) System.out.println(">>>>>> Hilo de tiempo real inicializado");
            } catch (MemoryAccessError e){ System.out.println("Error en el acceso a memoria por parte del hilo!!!");};
        }
        return feasibility;
    }

    public boolean AddRTBehaviour(SporadicBehaviours b)
    {
        RelativeTime minInterarrival,cost,deadline;
        int priority;
        boolean feasibility;

        minInterarrival = b.GetminInterarrival();
        cost = b.GetCost();
        deadline = b.GetDeadline();
        priority = b.GetPriority();

        feasibility=false;

        SchedulingParameters scheduling = new PriorityParameters(priority);
        ReleaseParameters release = new SporadicParameters(minInterarrival,cost,deadline,null,null);
        //De momento usamos Immortal memory, pero debemos usar ScopeMemory.
        //Problema: calcular el espacio necesario.
        //MemoryArea area = ImmortalMemory.instance();
        // NoHeap
        RealtimeThread rt= new RTThread(b,scheduling,release);//,area);

        if (b.GetFactibility())
        {
            feasibility = rt.addToFeasibility(); // si forzamos
        }
        else
        {
            feasibility = rt.addIfFeasible(); // si no forzamos
        }


        if (feasibility)
        {
            try
            {
                rt.start();
            } catch (MemoryAccessError e){};
            try {
                rt.join();
            } catch (Exception e) {};

        }
        return feasibility;
    }


    public boolean AddRTBehaviour(AperiodicBehaviours b)
    {

        RelativeTime cost,deadline;
        int priority;
        boolean feasibility;


        cost = b.GetCost();
        deadline = b.GetDeadline();
        priority = b.GetPriority();

        feasibility=false;

        SchedulingParameters scheduling = new PriorityParameters(priority);
        ReleaseParameters release = new AperiodicParameters(cost,deadline,null,null);
        RealtimeThread rt= new RTThread(b,scheduling,release);//,area);

        if (b.GetFactibility()) {
            feasibility = rt.addToFeasibility(); // si forzamos
        }
        else {
            feasibility = rt.addIfFeasible(); // si no forzamos
        }


        if (feasibility) {
            try {

                rt.start();

            } catch (MemoryAccessError e){};
            try {
                rt.join();
            } catch (Exception e) {};
        }
        return feasibility;
    }


    protected void esperar ( long espera ) {

        long tantes, tahora;
        long cont=0;
        espera--;

        while (cont <espera){ // este bucle espera "espera" miliseg
            tantes=System.currentTimeMillis();
            tahora=System.currentTimeMillis();
            while (tahora-tantes < 1) // este bucle espera 1 msg
                tahora=System.currentTimeMillis();
            cont++;
        } */
    }
}

