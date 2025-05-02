from collections import deque

def encontrarCaminoOptimo(n: int, e: int, listaPlataformas: list):
    if e >= n: 
        return "T" + str(n)

    # [nodo: (nodoAdyacente, movimiento)]
    grafo = []
    for i in range(n+1):
        grafo.append([])

    for i in range(n):
        if listaPlataformas[i] != -1:
            if ((i+1 <= n) and (listaPlataformas[i+1] != -1)):
                grafo[i].append((i+1, 'C+'))

            if (i-1 >= 0) and (listaPlataformas[i-1] != -1):
                grafo[i].append((i-1, 'C-'))

            if (listaPlataformas[i] > 0):
                salto = listaPlataformas[i]

                if ((i+salto <= n) and (listaPlataformas[i+salto] != -1)):
                    grafo[i].append((i+salto, 'S+'))

                if ((i-salto >= 0) and (listaPlataformas[i-salto] != -1)):
                    grafo[i].append((i-salto, 'S-'))

    q = deque()
    visitados = []

    q.append((0, e, []))
    visitados.append((0, e))
    while q:
        nodo = q.popleft()
        numNodo = nodo[0]
        energiaRestante = nodo[1]
        movidas = nodo[2]

        if (numNodo == n):
            mensaje = str(len(movidas)) + " " + " ".join(movidas)
            return mensaje

        for nodoValido in grafo[numNodo]:
            vecino = nodoValido[0]
            movida = nodoValido[1]

            estado = (vecino, energiaRestante)
            if (estado not in visitados):
                visitados.append(estado)
                q.append((vecino, energiaRestante, movidas+[movida]))

        for destino in range(n+1):
            if ((destino != numNodo) and (listaPlataformas[destino] != -1)):
                costoReal = destino-numNodo
                costo = abs(costoReal)

                if (energiaRestante >= costo):
                    nuevaEnergia = energiaRestante - costo
                    move = "T" + str(costoReal)

                    estado = (destino, nuevaEnergia)
                    if (estado not in visitados):
                        visitados.append(estado)
                        q.append((destino, nuevaEnergia, movidas + [move]))

    return "NO SE PUEDE"





def main():
    numCasos = int(input())
    casosTotales = []

    for _ in range(numCasos):
        plataformasYEnergia = list(map(int, input().split()))
        numPlataformas = plataformasYEnergia[0]
        energia = plataformasYEnergia[1]
        plataformasRobot = list(map(int, input().split()))
        parejasPlataformaPoder = list(map(int, input().split()))

        listaCaso = [0]*(numPlataformas+1)
        for i in range(0, len(plataformasRobot)):
            listaCaso[plataformasRobot[i]] = -1

        for i in range(0, len(parejasPlataformaPoder), 2):
            listaCaso[parejasPlataformaPoder[i]] = parejasPlataformaPoder[i+1]

        caso = (numPlataformas, energia, listaCaso)

        casosTotales.append(caso)
    
    for c in casosTotales:
        print(encontrarCaminoOptimo(c[0], c[1], c[2]))

main()