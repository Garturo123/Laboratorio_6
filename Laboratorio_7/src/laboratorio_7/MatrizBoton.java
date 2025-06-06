public void actionPerformed(ActionEvent evt) {
    int filas = Integer.parseInt(txtFilas.getText());
    int columnas = Integer.parseInt(txtColumnas.getText());

    panel.removeAll();
    panel.setPreferredSize(new Dimension(columnas * 110, filas * 110));

    Boton[] botones = new Boton[36];
    Juego juego = new Juego();

    int x = 0, y = 0;
    for (int i = 0; i < 36; i++) {
        botones[i] = new Boton(x, y, 100, 100);
        panel.add(botones[i]);
        x += 110;
        if ((i + 1) % 6 == 0) { x = 0; y += 110; }
    }

    juego.asignarImagenes(botones);

    panel.revalidate(); // mejor que updateUI()
    panel.repaint();
}
