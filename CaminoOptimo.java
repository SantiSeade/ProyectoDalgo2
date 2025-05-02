import java.util.*;

public class CaminoOptimo {

    static String encontrarCaminoOptimo(int n, int e, List<Integer> listaPlataformas) {
        if (e >= n) {
            return "T" + n;
        }

        List<List<Pair>> grafo = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            grafo.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            if (listaPlataformas.get(i) != -1) {
                if (i + 1 <= n && listaPlataformas.get(i + 1) != -1) {
                    grafo.get(i).add(new Pair(i + 1, "C+"));
                }
                if (i - 1 >= 0 && listaPlataformas.get(i - 1) != -1) {
                    grafo.get(i).add(new Pair(i - 1, "C-"));
                }

                int salto = listaPlataformas.get(i);
                if (salto > 0) {
                    if (i + salto <= n && listaPlataformas.get(i + salto) != -1) {
                        grafo.get(i).add(new Pair(i + salto, "S+"));
                    }
                    if (i - salto >= 0 && listaPlataformas.get(i - salto) != -1) {
                        grafo.get(i).add(new Pair(i - salto, "S-"));
                    }
                }
            }
        }

        Queue<State> q = new ArrayDeque<>();
        Set<String> visitados = new HashSet<>();

        q.add(new State(0, e, new ArrayList<>()));
        visitados.add(0 + "," + e);

        while (!q.isEmpty()) {
            State actual = q.poll();

            if (actual.nodo == n) {
                return actual.movidas.size() + " " + String.join(" ", actual.movidas);
            }

            for (Pair vecino : grafo.get(actual.nodo)) {
                String estado = vecino.destino + "," + actual.energia;
                if (!visitados.contains(estado)) {
                    List<String> nuevaLista = new ArrayList<>(actual.movidas);
                    nuevaLista.add(vecino.movida);
                    q.add(new State(vecino.destino, actual.energia, nuevaLista));
                    visitados.add(estado);
                }
            }

            for (int destino = 0; destino <= n; destino++) {
                if (destino != actual.nodo && listaPlataformas.get(destino) != -1) {
                    int costoReal = destino - actual.nodo;
                    int costo = Math.abs(costoReal);

                    if (actual.energia >= costo) {
                        int nuevaEnergia = actual.energia - costo;
                        String move = "T" + costoReal;
                        String estado = destino + "," + nuevaEnergia;

                        if (!visitados.contains(estado)) {
                            List<String> nuevaLista = new ArrayList<>(actual.movidas);
                            nuevaLista.add(move);
                            q.add(new State(destino, nuevaEnergia, nuevaLista));
                            visitados.add(estado);
                        }
                    }
                }
            }
        }

        return "NO SE PUEDE";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numCasos = Integer.parseInt(scanner.nextLine());

        for (int t = 0; t < numCasos; t++) {
            String[] plataformasYEnergia = scanner.nextLine().split(" ");
            int numPlataformas = Integer.parseInt(plataformasYEnergia[0]);
            int energia = Integer.parseInt(plataformasYEnergia[1]);

            String[] plataformasRobotStr = scanner.nextLine().split(" ");
            String[] parejasPlataformaPoderStr = scanner.nextLine().split(" ");

            List<Integer> listaCaso = new ArrayList<>(Collections.nCopies(numPlataformas + 1, 0));

            for (String s : plataformasRobotStr) {
                int index = Integer.parseInt(s);
                listaCaso.set(index, -1);
            }

            for (int i = 0; i < parejasPlataformaPoderStr.length; i += 2) {
                int index = Integer.parseInt(parejasPlataformaPoderStr[i]);
                int value = Integer.parseInt(parejasPlataformaPoderStr[i + 1]);
                listaCaso.set(index, value);
            }

            System.out.println(encontrarCaminoOptimo(numPlataformas, energia, listaCaso));
        }

        scanner.close();
    }

    static class Pair {
        int destino;
        String movida;

        Pair(int destino, String movida) {
            this.destino = destino;
            this.movida = movida;
        }
    }

    static class State {
        int nodo;
        int energia;
        List<String> movidas;

        State(int nodo, int energia, List<String> movidas) {
            this.nodo = nodo;
            this.energia = energia;
            this.movidas = movidas;
        }
    }
}
