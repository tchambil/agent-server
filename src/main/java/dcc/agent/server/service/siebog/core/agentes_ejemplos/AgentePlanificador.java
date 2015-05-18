//import java.awt.Point;

package dcc.agent.server.service.siebog.core.agentes_ejemplos;


import dcc.agent.server.service.siebog.core.Agents.RTAgent;

/*
 * Creado el 03-jun-2005
 *
 * TODO Para cambiar la plantilla de este archivo generado, vaya a
 * Ventana - Preferencias - Java - Estilo de código - Plantillas de código
 */

/**
 * @author root
 *         <p/>
 *         TODO Para cambiar la plantilla de este comentario generado, vaya a
 *         Ventana - Preferencias - Java - Estilo de código - Plantillas de código
 */

public class AgentePlanificador extends RTAgent {


    int indicepunto=0;

/*
    public AgentePlanificador(Platform platform, String name, Pizarra Tabla) {

        //super(platform,name);
        if (platform.Debug) System.out.println("INICIALIZANDO AGENTE PLANIFICADOR....");
        int i;
        double cost;
        pizarra = Tabla;

        Voraz plan = new Voraz(pizarra);
        Point[] puntos = plan.Plan(pizarra.getPuntosMedios());
        pizarra.AddDestinos(puntos);

        for (i = 0; i < puntos.length; i++) {
            System.out.println("x: " + puntos[i].getX() + "    y:  " + puntos[i].getY());
        }

        indicepunto = 0;

    }

    class Acciones extends PeriodicBehaviours {

        public void Task() {
            long tiempo_coste, tiempo, tiempo_deadline;
            //  RelativeTime deadline;
            if (platform.Verbose) System.out.println("Ejecutando tarea ACCIONES del agente PLANIFICADOR .......");

            tiempo_coste = tiempo = System.currentTimeMillis();

            if (platform.Kiwi) {
                platform.kiwi.poner(tiempo, "START", 2);
                platform.kiwi.poner(tiempo, "READY-B", 2);
                platform.kiwi.poner(tiempo, "EXEC-B", 2);
                // deadline = GetDeadline();
                tiempo_deadline = System.currentTimeMillis();//deadline.getMilliseconds();
                tiempo = tiempo + tiempo_deadline;
                platform.kiwi.poner(tiempo, "DEADLINE", 2);
            }

            double xactual, yactual, angulo, angulo2, xdestino, ydestino;
            Point destino, puntomedio;
            double A, B, C;

            xactual = pizarra.getPuntoActual().getX();
            yactual = pizarra.getPuntoActual().getY();
            angulo = pizarra.getAngulo();

            destino = pizarra.getDestino();

            if (destino != null) {
                xdestino = destino.getX();
                ydestino = destino.getY();
                StringBuffer accion = new StringBuffer();

                //en el caso de que el origen y destino coincidan en el eje x
                if ((xdestino - 100.0 < xactual) && (xactual < xdestino + 100.0)) {
                    if ((ydestino - 100.0 < yactual) && (yactual < ydestino + 100.0)) {
                        accion.append("Stop");
                    } else if (ydestino + 100.0 <= yactual) {
                        if (((-90.0 - 10.0) < angulo) && (angulo < (-90.0 + 10.0))) {
                            accion.append("Avanzar");
                        } else if ((-80.0 <= angulo) && (angulo < 90.0)) {
                            accion.append("Derecha");
                        } else {
                            accion.append("Izquierda");
                        }
                    } else {
                        if (((90.0 - 10.0) < angulo) && (angulo < (90.0 + 10.0))) {
                            accion.append("Avanzar");
                        } else if ((-90.0 < angulo) && (angulo <= 80.0)) {
                            accion.append("Izquierda");
                        } else {
                            accion.append("Derecha");
                        }
                    }
                }

                //en el caso de que el el eje x actual sea menor que el destino
                else if (xactual <= (xdestino - 100.0)) {
                    if (yactual <= (ydestino - 100.0)) {
                        puntomedio = new Point(xdestino, yactual);
                        A = pizarra.distancia(pizarra.getPuntoActual(), puntomedio);
                        B = pizarra.distancia(pizarra.getPuntoActual(), destino);

                        C = A / B;
                        angulo2 = Math.acos(C);
                        angulo2 = Math.toDegrees(angulo2);

                        if (((angulo2 - 10.0) < angulo) && (angulo < (angulo2 + 10.0))) {
                            accion.append("Avanzar");
                        } else if (((-180.0 + angulo2) < angulo) && (angulo <= (angulo2 - 10.0))) {
                            accion.append("Izquierda");
                        } else {
                            accion.append("Derecha");
                        }
                    } else if ((ydestino + 100.0) <= yactual) {
                        puntomedio = new Point(xdestino, yactual);
                        A = pizarra.distancia(pizarra.getPuntoActual(), puntomedio);
                        B = pizarra.distancia(pizarra.getPuntoActual(), destino);
                        C = A / B;

                        angulo2 = Math.acos(C);
                        angulo2 = 0.0 - Math.toDegrees(angulo2);

                        if (((angulo2 - 10.0) < angulo) && (angulo < (angulo2 + 10.0))) {
                            accion.append("Avanzar");
                        } else if (((angulo2 + 10.0) <= angulo) && (angulo < 180.0 + angulo2)) {
                            accion.append("Derecha");
                        } else {
                            accion.append("Izquierda");
                        }
                    } else {
                        if (-10 < angulo && angulo < 10) {
                            accion.append("Avanzar");
                        } else if (10.0 <= angulo && angulo < 180.0) {
                            accion.append("Derecha");
                        } else {
                            accion.append("Izquierda");
                        }
                    }
                }

                //en el caso de que el el eje x actual sea mayor que el destino
                else if ((xdestino - 100.0) <= xactual) {
                    if ((ydestino - 100.0) <= yactual) {
                        puntomedio = new Point(xdestino, yactual);
                        A = pizarra.distancia(pizarra.getPuntoActual(), puntomedio);
                        B = pizarra.distancia(pizarra.getPuntoActual(), destino);

                        C = A / B;
                        angulo2 = Math.acos(C);
                        angulo2 = -180 + Math.toDegrees(angulo2);

                        if (((angulo2 - 10.0) < angulo) && (angulo < (angulo2 + 10.0))) {
                            accion.append("Avanzar");
                        } else if (((angulo2 + 10.0) <= angulo) && (angulo < Math.toDegrees(Math.acos(C)))) {
                            accion.append("Derecha");
                        } else {
                            accion.append("Izquierda");
                        }
                    } else if (yactual <= (ydestino + 100.0)) {
                        puntomedio = new Point(xdestino, yactual);
                        A = pizarra.distancia(pizarra.getPuntoActual(), puntomedio);
                        B = pizarra.distancia(pizarra.getPuntoActual(), destino);

                        C = A / B;
                        angulo2 = Math.acos(C);
                        angulo2 = 180 - Math.toDegrees(angulo2);

                        if (((angulo2 - 10.0) < angulo) && (angulo < (angulo2 + 10.0))) {
                            accion.append("Avanzar");
                        } else if (((0 - Math.toDegrees(Math.acos(C))) < angulo) && (angulo <= angulo2 - 10.0)) {
                            accion.append("Izquierda");
                        } else {
                            accion.append("Derecha");
                        }
                    } else {
                        if (((180.0 - 10.0) < angulo) && (angulo < (-180.0 + 10.0))) {
                            accion.append("Avanzar");
                        } else if ((0.0 < angulo) && (angulo <= (180.0 - 10.0))) {
                            accion.append("Izquierda");
                        } else {
                            accion.append("Derecha");
                        }
                    }
                }

                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                accion.append(";5;");
                System.out.println(accion);

                msg.setContent(accion.toString());
                msg.setSender(aidAgent);

                //buscamos el AID del agente2
                AID receiver = platform.getAMS().Search("AgenteControlador");
                msg.setReceiver(receiver);
                Send(msg);
            }

            if (platform.Kiwi) {
                tiempo = System.currentTimeMillis();
                platform.kiwi.poner(tiempo, "EXEC-E", 2);
                platform.kiwi.poner(tiempo, "READY-E", 2);
                platform.kiwi.poner(tiempo, "STOP", 2);
            }

            tiempo_coste = System.currentTimeMillis() - tiempo_coste;
            System.out.println("coste de la tarea navegación: " + tiempo_coste);
        } //fin task
    } */

}
