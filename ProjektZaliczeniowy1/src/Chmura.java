import jdk.internal.util.xml.impl.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiPredicate;

public class Chmura implements ChmuraAbst {
    private Map<Integer, Map<Integer, Byt>> chmura;


    public Chmura(){
        chmura = new ConcurrentHashMap<>();
    }

    public Chmura(BiPredicate<Integer,Integer> stan){
        for(Integer x:chmura.keySet())
            for(Integer y:chmura.get(x).keySet())
                if(stan.test(x,y)){
                    if(chmura.containsKey(x))
                        chmura.get(x).put(y,new Byt());
                    else{
                        Map<Integer,Byt> rzad = new HashMap<>();
                        rzad.put(y,new Byt());
                        chmura.put(x,rzad);
                    }
        }
    }


    @Override
    public Byt ustaw(int x, int y) throws NiebytException {
        Map<Integer, Byt> y_chmura = new HashMap<>();
        Byt byt = new Byt();

        y_chmura.put(y, byt);
        chmura.putIfAbsent(x,y_chmura);
        y_chmura = chmura.get(x);

        synchronized(y_chmura){
            if(!y_chmura.containsKey(y)){
                y_chmura.put(y,byt);
            }else{
                throw new NiebytException();
            }
        }

//        synchronized(chmura) {
//
//            if (!chmura.containsKey(x)) {
//                Map<Integer, Byt> rzad = new HashMap<>();
//                byt = new Byt();
//                rzad.put(y, byt);
//                chmura.putIfAbsent(x, rzad);
//            } else {
//                Map<Integer, Byt> rzad = chmura.get(x);
//                if (!rzad.containsKey(y)) {
//                    byt = new Byt();
//                    rzad.put(y, byt);
//                } else
//                    byt = rzad.get(y);
//            }
//        }
        return byt;
            //ewentualnie zrób tak żeby zwracało wyjatek gdy juz jest byt w chmurze na ty miejscu
    }

    @Override
    public void przestaw(Collection<Byt> byty, int dx, int dy) throws NiebytException, InterruptedException {















    }

    @Override
    public void kasuj(Byt byt) throws NiebytException {
        synchronized(chmura){
            for(Integer x:chmura.keySet()) {
                Map<Integer, Byt> rzad = chmura.get(x);

                for (Integer y : rzad.keySet())
                    if (byt.equals(rzad.get(y))) {
                        rzad.remove(y);
                        return;
                    }
            }
        }
        throw new NiebytException();
    }

    @Override
    public int[] miejsce(Byt byt) {
        synchronized(chmura){
            for(Integer x:chmura.keySet()) {
                Map<Integer, Byt> rzad = chmura.get(x);

                for (Integer y : rzad.keySet())
                    if (byt.equals(rzad.get(y))) {
                        int[] wspolrzedne = {x,y};
                        return wspolrzedne;
                    }
            }
        }
        return null;
    }
}
