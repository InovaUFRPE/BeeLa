package com.beela.beela.Lugar;

import com.beela.beela.Entidades.LugarGoogle;
import com.beela.beela.Entidades.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
//import br.ufrpe.beela.lugar.dominio.Lugar;
//import br.ufrpe.beela.pessoa.dominio.Pessoa;


/**
 * Crédito por este algoritmo:
 *      Site: http://www.baeldung.com/java-collaborative-filtering-recommendations
 *      GitHub: https://github.com/eugenp/tutorials/tree/master/algorithms/src/main/java/com/baeldung/algorithms/slope_one
 */


public class SlopeOne {

    private  Map<LugarGoogle, Map<LugarGoogle, Double>> matrizDeDiferenca = new HashMap<>();
    private  Map<LugarGoogle, Map<LugarGoogle, Integer>> matrizDeFrequencia = new HashMap<>();
    private  Map<Usuario, HashMap<LugarGoogle, Double>> dadosDeSaida = new HashMap<>();

    private  Map<Usuario, HashMap<LugarGoogle, Double>> matrizInicial= new HashMap<>();
    private  Map<Usuario, HashMap<LugarGoogle, Double>> matrizFinal = new HashMap<>();

    private  ArrayList<LugarGoogle> listaLugares= new ArrayList<LugarGoogle>();
    private  ArrayList<LugarGoogle> listaRecomendados= new ArrayList<LugarGoogle>();


    public SlopeOne(Map<Usuario, HashMap<LugarGoogle, Double>> matriz,ArrayList<LugarGoogle> listaLugar){
        matrizInicial=matriz;
        listaLugares=listaLugar;
    }

    public  void slopeOne() {
        buildDifferencesMatrix(matrizInicial);
        predict(matrizInicial);

    }

    /**
     *  Com base nos dados disponíveis, é calculado as relações entre os
     *  usuários e número de ocorrências dos lugares
     */

    private void buildDifferencesMatrix(Map<Usuario, HashMap<LugarGoogle, Double>> data) {
        for (HashMap<LugarGoogle, Double> user : data.values()) {
            for (Entry<LugarGoogle, Double> e : user.entrySet()) {
                if (!matrizDeDiferenca.containsKey(e.getKey())) {
                    matrizDeDiferenca.put(e.getKey(), new HashMap<LugarGoogle, Double>());
                    matrizDeFrequencia.put(e.getKey(), new HashMap<LugarGoogle, Integer>());
                }

                for (Entry<LugarGoogle, Double> e2 : user.entrySet()) {
                    int oldCount = 0;
                    if (matrizDeFrequencia.get(e.getKey()).containsKey(e2.getKey())) {
                        oldCount = matrizDeFrequencia.get(e.getKey()).get(e2.getKey()).intValue();
                    }
                    double oldDiff = 0.0;
                    if (matrizDeDiferenca.get(e.getKey()).containsKey(e2.getKey())) {
                        oldDiff = matrizDeDiferenca.get(e.getKey()).get(e2.getKey()).doubleValue();
                    }
                    double observedDiff = e.getValue() - e2.getValue();
                    matrizDeFrequencia.get(e.getKey()).put(e2.getKey(), oldCount + 1);
                    matrizDeDiferenca.get(e.getKey()).put(e2.getKey(), oldDiff + observedDiff);
                }
            }
        }

        for (LugarGoogle j : matrizDeDiferenca.keySet()) {
            for (LugarGoogle i : matrizDeDiferenca.get(j).keySet()) {
                double oldValue = matrizDeDiferenca.get(j).get(i).doubleValue();
                int count = matrizDeFrequencia.get(j).get(i).intValue();
                matrizDeDiferenca.get(j).put(i, oldValue / count);
            }
        }
    }

    /**
     *  Com base nos dados existentes, prevê todas as classificações faltantes.
     *  São dados de usuários existentes e classificações de seus lugares.
     */

    private  void predict(Map<Usuario, HashMap<LugarGoogle, Double>> data) {
        HashMap<LugarGoogle, Double> uPred = new HashMap<LugarGoogle, Double>();
        HashMap<LugarGoogle, Integer> uFreq = new HashMap<LugarGoogle, Integer>();

        for (LugarGoogle j : matrizDeDiferenca.keySet()) {
            uFreq.put(j, 0);
            uPred.put(j, 0.0);
        }

        for (Entry<Usuario, HashMap<LugarGoogle, Double>> e : data.entrySet()) {
            for (LugarGoogle j : e.getValue().keySet()) {
                for (LugarGoogle k : matrizDeDiferenca.keySet()) {
                    try {
                        double predictedValue = matrizDeDiferenca.get(k).get(j).doubleValue() + e.getValue().get(j).doubleValue();
                        double finalValue = predictedValue * matrizDeFrequencia.get(k).get(j).intValue();
                        uPred.put(k, uPred.get(k) + finalValue);
                        uFreq.put(k, uFreq.get(k) + matrizDeFrequencia.get(k).get(j).intValue());
                    } catch (NullPointerException e1) {
                    }
                }
            }


            HashMap<LugarGoogle, Double> clean = new HashMap<LugarGoogle, Double>();
            for (LugarGoogle j : uPred.keySet()) {
                Boolean v = clean.containsKey(j);
                if (uFreq.get(j) > 0 && !v) {
                    clean.put(j, uPred.get(j).doubleValue() / uFreq.get(j).intValue());
                }
            }
            for (LugarGoogle j : listaLugares) {
                Boolean v = clean.containsKey(j);
                if (e.getValue().containsKey(j) && !v) {
                    clean.put(j, e.getValue().get(j));
                }
            }
            dadosDeSaida.put(e.getKey(), clean);
        }
        matrizFinal = dadosDeSaida;
    }
    public ArrayList<LugarGoogle> getListaRecomendados(Usuario pessoa) {
        HashMap<LugarGoogle,Double> matrizF = matrizFinal.get(pessoa);
        getRecomendadosAux(matrizF);
        return listaRecomendados;
    }
    public void getRecomendadosAux(HashMap<LugarGoogle,Double> matrizFinal){
        HashMap<LugarGoogle,Double> m = new HashMap<LugarGoogle,Double>();

        ArrayList<Integer> l = new ArrayList<Integer>();
        try {
            for (LugarGoogle lugar : matrizFinal.keySet()) {
                int x = lugar.getId();
                if (!l.contains(x)) {
                    l.add(lugar.getId());
                    m.put(lugar, matrizFinal.get(lugar).doubleValue());
                    lugar.setNotaProvisoria(matrizFinal.get(lugar).doubleValue());
                    listaRecomendados.add(lugar);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
