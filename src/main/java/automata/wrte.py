from graphviz import Digraph

def crear_automata_write():
    dot = Digraph(comment='Autómata Write', format='pdf')
    
    # Configurar el diagrama en orientación horizontal
    dot.attr(rankdir='LR', size='8,5')
    
    # Definir los nodos (estados)
    # q0 es el estado inicial, q5 es el estado de aceptación
    estados = {
        'q0': 'q0',
        'q1': 'q1',
        'q2': 'q2',
        'q3': 'q3',
        'q4': 'q4',
        'q5': 'q5',
    }
    
    # Agregar los nodos al diagrama
    for estado in estados:
        if estado == 'q5':
            dot.node(estado, estados[estado], shape='doublecircle')  # Estado de aceptación
        else:
            dot.node(estado, estados[estado], shape='circle')
    
    # Definir las transiciones
    dot.edge('q0', 'q1', label='"write"')
    dot.edge('q1', 'q2', label='"("')
    
    # Transiciones para cadenas o variables en q2
    dot.edge('q2', 'q3', label='Cadena o Variable válida')
    
    # Transiciones desde q3
    dot.edge('q3', 'q2', label='","')
    dot.edge('q3', 'q4', label='")"')
    
    # Transición de q4 a q5
    dot.edge('q4', 'q5', label='";"')
    
    return dot

if __name__ == "__main__":
    automata = crear_automata_write()
    # Especifica la ruta donde deseas guardar el PDF
    ruta_guardado = r'C:\Programacion\Alpaca\automata\AutomataWrite.pdf'
    automata.render(filename='AutomataWrite', directory=r'C:\Programacion\Alpaca\auto', cleanup=True)
    print(f'Diagrama generado y guardado en {ruta_guardado}')
