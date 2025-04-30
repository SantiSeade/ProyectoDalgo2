def encontrarCaminoOptimo(n: int, e: int, listaPlataformas: list):
    return None

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
        encontrarCaminoOptimo(c[0], c[1], c[2])

main()