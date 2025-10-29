package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import cuentas.*;
import intereses.*;
import procesos.ProcesoMensual;
import archivo.GeneradorTXT;
import observer.*;
import acceso.AccesoRemoto;
import excepciones.EntradaInvalida;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Clase principal de la interfaz grafica de PumaBank.
 * Utiliza JavaFX para crear una aplicacion de escritorio con todas las funcionalidades del sistema.
 * @author LasPamparas
 * @version 1.0
 */
public class PumaBankGUI extends Application {

    private List<Cuenta> cuentas;
    private Portafolio portafolio;
    private ProcesoMensual procesoMensual;
    private TextArea logArea;
    private java.util.Map<String, Portafolio> portafoliosPersonales;

    /**
     * Metodo principal que lanza la aplicacion.
     * @param args Argumentos de la linea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Metodo de inicio de la aplicacion JavaFX.
     * @param primaryStage El escenario principal de la aplicacion.
     */
    @Override
    public void start(Stage primaryStage) {
        this.cuentas = new ArrayList<>();
        this.portafolio = new Portafolio("Portafolio Principal");
        this.procesoMensual = new ProcesoMensual();
        this.portafoliosPersonales = new java.util.HashMap<>();

        // Cuentas de prueba - Simulando que cada cliente tiene multiples cuentas
        // LUIS tiene 2 cuentas: Ahorro y Nomina
        Cuenta cuentaLuisAhorro = new Cuenta("LUIS", 3000.0, new InteresMensual(), new EstadoActiva(), "2847");
        Cuenta cuentaLuisNomina = new Cuenta("LUIS", 1500.0, new InteresMensual(), new EstadoActiva(), "2847");

        // JULIO tiene 2 cuentas: Ahorro e Inversion
        Cuenta cuentaJulioAhorro = new Cuenta("JULIO", 55000.0, new InteresAnual(), new EstadoActiva(), "9531");
        Cuenta cuentaJulioInversion = new Cuenta("JULIO", 25000.0, new InteresAnual(), new EstadoActiva(), "9531");

        // INVERSIONISTA tiene 3 cuentas
        Cuenta cuentaInv1 = new Cuenta("INVERSIONISTA", 120000.0, new InteresPremium(), new EstadoActiva(), "1122");
        Cuenta cuentaInv2 = new Cuenta("INVERSIONISTA", 80000.0, new InteresPremium(), new EstadoActiva(), "1122");
        Cuenta cuentaInv3 = new Cuenta("INVERSIONISTA", 50000.0, new InteresPremium(), new EstadoActiva(), "1122");

        cuentas.add(cuentaLuisAhorro);
        cuentas.add(cuentaLuisNomina);
        cuentas.add(cuentaJulioAhorro);
        cuentas.add(cuentaJulioInversion);
        cuentas.add(cuentaInv1);
        cuentas.add(cuentaInv2);
        cuentas.add(cuentaInv3);

        procesoMensual.agregarCuenta(cuentaLuisAhorro);
        procesoMensual.agregarCuenta(cuentaLuisNomina);
        procesoMensual.agregarCuenta(cuentaJulioAhorro);
        procesoMensual.agregarCuenta(cuentaJulioInversion);
        procesoMensual.agregarCuenta(cuentaInv1);
        procesoMensual.agregarCuenta(cuentaInv2);
        procesoMensual.agregarCuenta(cuentaInv3);

        // Crear portafolios personales para cada cliente
        Portafolio portfolioLuis = new Portafolio("Portafolio de LUIS");
        portfolioLuis.agregar(cuentaLuisAhorro);
        portfolioLuis.agregar(cuentaLuisNomina);
        portafoliosPersonales.put("LUIS", portfolioLuis);

        Portafolio portfolioJulio = new Portafolio("Portafolio de JULIO");
        portfolioJulio.agregar(cuentaJulioAhorro);
        portfolioJulio.agregar(cuentaJulioInversion);
        portafoliosPersonales.put("JULIO", portfolioJulio);

        Portafolio portfolioInv = new Portafolio("Portafolio de INVERSIONISTA");
        portfolioInv.agregar(cuentaInv1);
        portfolioInv.agregar(cuentaInv2);
        portfolioInv.agregar(cuentaInv3);
        portafoliosPersonales.put("INVERSIONISTA", portfolioInv);

        // Portafolio global (estructura organizativa del banco)
        portafolio.agregar(portfolioLuis);
        portafolio.agregar(portfolioJulio);
        portafolio.agregar(portfolioInv);

        primaryStage.setTitle("PumaBank - Sistema Bancario Digital");
        primaryStage.setScene(createMainScene());
        primaryStage.setWidth(900);
        primaryStage.setHeight(700);
        primaryStage.show();

        log("✅ Sistema PumaBank iniciado correctamente");
        log("📊 Cuentas de prueba cargadas: " + cuentas.size());
    }

    /**
     * Crea la escena principal de la aplicacion.
     * @return La escena principal.
     */
    private Scene createMainScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #f8f9fa, #e9ecef);");

        VBox header = createHeader();
        root.setTop(header);

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setStyle("-fx-tab-min-width: 150px; -fx-font-size: 13px;");

        Tab loginTab = new Tab("🔑 Acceso a Cuenta", createLoginPanel());
        Tab portfolioTab = new Tab("💼 Portafolio", createPortfolioPanel());
        Tab crearCuentaTab = new Tab("➕ Nueva Cuenta", createCrearCuentaPanel());
        Tab procesoTab = new Tab("📅 Proceso Mensual", createProcesoMensualPanel());
        Tab estadisticasTab = new Tab("📊 Estadisticas", createEstadisticasPanel());

        tabPane.getTabs().addAll(loginTab, portfolioTab, crearCuentaTab, procesoTab, estadisticasTab);

        root.setCenter(tabPane);

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefHeight(150);
        logArea.setStyle("-fx-control-inner-background: #1e1e1e; -fx-text-fill: #4ec9b0; " +
                         "-fx-font-family: 'Consolas', 'Courier New'; -fx-font-size: 11px;");

        TitledPane logPane = new TitledPane("📋 Log del Sistema", logArea);
        logPane.setCollapsible(true);
        logPane.setExpanded(true);
        logPane.setStyle("-fx-font-weight: bold;");
        root.setBottom(logPane);

        return new Scene(root);
    }

    /**
     * Crea el encabezado de la aplicacion.
     * @return El encabezado como un VBox.
     */
    private VBox createHeader() {
        VBox header = new VBox();
        header.setStyle("-fx-background-color: linear-gradient(to right, #1e3c72, #2a5298); -fx-padding: 20;");
        header.setAlignment(Pos.CENTER);

        Label title = new Label("🏦 PUMABANK");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        title.setTextFill(Color.WHITE);

        Label subtitle = new Label("Sistema Bancario Digital - Patrones de Diseno");
        subtitle.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        subtitle.setTextFill(Color.LIGHTGRAY);

        header.getChildren().addAll(title, subtitle);
        return header;
    }

    /**
     * Crea el panel del portafolio.
     * @return El panel del portafolio como un BorderPane.
     */
    private BorderPane createPortfolioPanel() {
        BorderPane panel = new BorderPane();
        panel.setPadding(new Insets(20));
        panel.setStyle("-fx-background-color: white;");

        VBox centerPanel = new VBox(20);
        centerPanel.setPadding(new Insets(30));
        centerPanel.setAlignment(Pos.CENTER);

        Label title = new Label("💼 Estructura del Portafolio");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: #1e3c72;");

        Label subtitle = new Label("Vista de la organizacion de cuentas del sistema");
        subtitle.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        subtitle.setStyle("-fx-text-fill: #6c757d;");

        // Arbol del portafolio
        TreeItem<ComponenteBancario> rootItem = new TreeItem<>(portafolio);
        rootItem.setExpanded(true);
        buildTree(rootItem, portafolio);

        TreeView<ComponenteBancario> treeView = new TreeView<>(rootItem);
        treeView.setCellFactory(tv -> new ComponenteBancarioTreeCell());
        treeView.setPrefHeight(300);

        // Area de detalles con restriccion
        TextArea detailsArea = new TextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(Font.font("Courier New", 12));
        detailsArea.setWrapText(true);
        detailsArea.setPrefHeight(150);
        detailsArea.setStyle("-fx-control-inner-background: #f8f9fa;");
        detailsArea.setText("Seleccione una cuenta del arbol para ver informacion basica.\n\n" +
                           "⚠️ NOTA: Para ver detalles sensibles (saldo, operaciones), \n" +
                           "debe autenticarse usando la pestana 'Acceso a Cuenta'.");

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ComponenteBancario componente = newValue.getValue();

                // Solo mostrar informacion NO sensible
                StringBuilder info = new StringBuilder();
                info.append("════════════════════════════════════════════════\n");
                info.append("        INFORMACION GENERAL (Sin autenticacion)\n");
                info.append("════════════════════════════════════════════════\n\n");

                if (componente instanceof Cuenta) {
                    Cuenta c = (Cuenta) componente;
                    info.append("Tipo: Cuenta Individual\n");
                    info.append("Cliente: ").append(c.getCliente()).append("\n");
                    info.append("Estado: ").append(c.getEstado().getEstado()).append("\n");
                    info.append("Tipo de Interes: ").append(c.getEstrategiaInteres().getClass().getSimpleName().replace("Interes", "")).append("\n");
                    info.append("\n════════════════════════════════════════════════\n");
                    info.append("⚠️  INFORMACION PROTEGIDA\n");
                    info.append("════════════════════════════════════════════════\n\n");
                    info.append("El saldo y detalles sensibles están ocultos.\n");
                    info.append("Para ver esta informacion, inicie sesion en la\n");
                    info.append("pestana 'Acceso a Cuenta' con su NIP.\n");

                } else if (componente instanceof Portafolio) {
                    Portafolio p = (Portafolio) componente;
                    info.append("Tipo: Portafolio\n");
                    info.append("Nombre: ").append(p.getCliente()).append("\n");
                    info.append("Numero de componentes: ").append(p.getComponentes().size()).append("\n");
                    info.append("\n════════════════════════════════════════════════\n");
                    info.append("⚠️  INFORMACION AGREGADA PROTEGIDA\n");
                    info.append("════════════════════════════════════════════════\n\n");
                    info.append("El saldo total del portafolio está oculto por\n");
                    info.append("razones de seguridad y privacidad.\n");
                }

                detailsArea.setText(info.toString());
            }
        });

        // Panel informativo de seguridad
        VBox securityInfo = new VBox(10);
        securityInfo.setPadding(new Insets(15));
        securityInfo.setStyle("-fx-background-color: #fff3cd; -fx-border-color: #ffc107; " +
                             "-fx-border-radius: 5; -fx-background-radius: 5;");

        Label securityTitle = new Label("🔒 Politica de Privacidad");
        securityTitle.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        Label securityText = new Label(
            "Por seguridad, esta vista solo muestra la estructura organizativa.\n" +
            "Los saldos y detalles sensibles requieren autenticacion individual.");
        securityText.setFont(Font.font("Arial", FontWeight.NORMAL, 11));
        securityText.setWrapText(true);

        securityInfo.getChildren().addAll(securityTitle, securityText);

        Button exportButton = new Button("📄 Exportar Estructura (Admin)");
        exportButton.setStyle("-fx-background-color: #6c757d; -fx-text-fill: white; " +
                             "-fx-font-size: 12px; -fx-padding: 10 20; -fx-background-radius: 5;");
        exportButton.setOnAction(e -> {
            // Solicitar contrasena de administrador
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Autenticacion de Administrador");
            dialog.setHeaderText("Se requiere contrasena de administrador para exportar");
            dialog.setContentText("Contrasena:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(password -> {
                if (password.equals("admin123")) {
                    GeneradorTXT.exportarPortafolio(portafolio, "portafolio_admin.txt");
                    log("📄 Portafolio exportado por administrador: portafolio_admin.txt");
                    showAlert("Exportacion Exitosa",
                             "Archivo generado: portafolio_admin.txt\n(Con informacion sensible)",
                             Alert.AlertType.INFORMATION);
                } else {
                    log("❌ Intento fallido de exportacion: contrasena incorrecta");
                    showAlert("Error de Autenticacion",
                             "Contrasena de administrador incorrecta",
                             Alert.AlertType.ERROR);
                }
            });
        });

        centerPanel.getChildren().addAll(title, subtitle, securityInfo,
                                        new Label("Estructura del Portafolio:"), treeView,
                                        new Label("Informacion del Componente:"), detailsArea,
                                        exportButton);

        panel.setCenter(centerPanel);
        return panel;
    }

    /**
     * Construye el arbol del portafolio.
     * @param parent El item padre en el arbol.
     * @param component El componente bancario a agregar.
     */
    private void buildTree(TreeItem<ComponenteBancario> parent, ComponenteBancario component) {
        if (component instanceof Portafolio) {
            Portafolio p = (Portafolio) component;
            for (ComponenteBancario child : p.getComponentes()) {
                TreeItem<ComponenteBancario> childItem = new TreeItem<>(child);
                parent.getChildren().add(childItem);
                if (child instanceof Portafolio) {
                    childItem.setExpanded(true);
                    buildTree(childItem, child);
                }
            }
        }
    }

    /**
     * Crea el panel de inicio de sesion.
     * @return El panel de inicio de sesion como un VBox.
     */
    private VBox createLoginPanel() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(50));
        panel.setAlignment(Pos.CENTER);
        panel.setStyle("-fx-background-color: linear-gradient(to bottom right, #667eea, #764ba2);");

        // Card contenedor
        VBox card = new VBox(20);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(40));
        card.setMaxWidth(400);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; " +
                      "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 5);");

        Label titleLabel = new Label("🔐 Acceso Seguro");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        titleLabel.setStyle("-fx-text-fill: #1e3c72;");

        Label subtitleLabel = new Label("Ingrese sus credenciales para continuar");
        subtitleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        subtitleLabel.setStyle("-fx-text-fill: #6c757d;");

        VBox formBox = new VBox(15);
        formBox.setAlignment(Pos.CENTER);

        // Campo de usuario
        VBox userBox = new VBox(5);
        Label userLabel = new Label("Usuario");
        userLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        userLabel.setStyle("-fx-text-fill: #495057;");

        TextField userField = new TextField();
        userField.setPromptText("Ingrese su nombre de usuario");
        userField.setPrefWidth(320);
        userField.setPrefHeight(40);
        userField.setStyle("-fx-font-size: 14px; -fx-background-radius: 5; " +
                           "-fx-border-color: #ced4da; -fx-border-radius: 5;");
        userBox.getChildren().addAll(userLabel, userField);

        // Campo de NIP
        VBox nipBox = new VBox(5);
        Label nipLabel = new Label("NIP");
        nipLabel.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        nipLabel.setStyle("-fx-text-fill: #495057;");

        PasswordField nipField = new PasswordField();
        nipField.setPromptText("Ingrese su NIP de 4 digitos");
        nipField.setPrefWidth(320);
        nipField.setPrefHeight(40);
        nipField.setStyle("-fx-font-size: 14px; -fx-background-radius: 5; " +
                          "-fx-border-color: #ced4da; -fx-border-radius: 5;");
        nipBox.getChildren().addAll(nipLabel, nipField);

        formBox.getChildren().addAll(userBox, nipBox);

        Button loginButton = new Button("🔓 Iniciar Sesion");
        loginButton.setPrefWidth(320);
        loginButton.setPrefHeight(45);
        loginButton.setStyle("-fx-background-color: linear-gradient(to right, #667eea, #764ba2); " +
                             "-fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; " +
                             "-fx-background-radius: 5; -fx-cursor: hand;");

        // Efecto hover
        loginButton.setOnMouseEntered(e -> loginButton.setStyle(
            "-fx-background-color: linear-gradient(to right, #5568d3, #653a8b); " +
            "-fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; " +
            "-fx-background-radius: 5; -fx-cursor: hand;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle(
            "-fx-background-color: linear-gradient(to right, #667eea, #764ba2); " +
            "-fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; " +
            "-fx-background-radius: 5; -fx-cursor: hand;"));

        loginButton.setOnAction(e -> {
            String usuario = userField.getText().trim().toUpperCase();
            String nip = nipField.getText().trim();

            if (usuario.isEmpty() || nip.isEmpty()) {
                showAlert("Error", "Complete todos los campos", Alert.AlertType.WARNING);
                return;
            }

            Cuenta cuenta = null;
            for (Cuenta c : cuentas) {
                if (c.getCliente().equalsIgnoreCase(usuario)) {
                    cuenta = c;
                    break;
                }
            }

            if (cuenta == null) {
                showAlert("Error de Autenticacion", "Usuario no encontrado", Alert.AlertType.ERROR);
                log("❌ Intento fallido: Usuario '" + usuario + "' no existe");
                return;
            }

            AccesoRemoto acceso = new AccesoRemoto(cuenta);
            try {
                acceso.verificarNIP(nip);
                log("✅ Acceso concedido para: " + cuenta.getCliente());
                nipField.clear();
                userField.clear();
                showCuentaDashboard(cuenta);
            } catch (EntradaInvalida ex) {
                showAlert("Error de Autenticacion", ex.getMessage(), Alert.AlertType.ERROR);
                log("❌ Intento fallido de acceso para: " + cuenta.getCliente());
                nipField.clear();
            }
        });

        card.getChildren().addAll(titleLabel, subtitleLabel, formBox, loginButton);
        panel.getChildren().add(card);
        return panel;
    }

    /**
     * Muestra el panel de control de la cuenta con portafolio personal.
     * @param cuenta La cuenta principal del usuario (primera cuenta para autenticacion).
     */
    private void showCuentaDashboard(Cuenta cuenta) {
        Stage dashboardStage = new Stage();
        dashboardStage.setTitle("Dashboard - " + cuenta.getCliente());

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #f8f9fa, #e9ecef);");

        // Obtener portafolio personal del usuario
        Portafolio portafolioPersonal = portafoliosPersonales.get(cuenta.getCliente());

        // Header del dashboard
        VBox header = new VBox(10);
        header.setPadding(new Insets(25));
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: linear-gradient(to right, #1e3c72, #2a5298);");

        Label welcomeLabel = new Label("Bienvenido, " + cuenta.getCliente());
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        welcomeLabel.setTextFill(Color.WHITE);

        Label fechaLabel = new Label("Fecha: " + java.time.LocalDate.now().toString());
        fechaLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        fechaLabel.setTextFill(Color.LIGHTGRAY);

        header.getChildren().addAll(welcomeLabel, fechaLabel);
        root.setTop(header);

        // Panel de informacion
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        VBox centerPanel = new VBox(20);
        centerPanel.setPadding(new Insets(30));
        centerPanel.setAlignment(Pos.TOP_CENTER);

        // Tarjeta de SALDO GLOBAL del portafolio
        VBox saldoGlobalCard = createInfoCard("💼 SALDO GLOBAL DE PORTAFOLIO",
            String.format("$%,.2f", portafolioPersonal.getSaldo()), "#0066cc");
        saldoGlobalCard.setPrefWidth(700);
        saldoGlobalCard.setPrefHeight(100);

        // Titulo de mis cuentas
        Label misCuentasTitle = new Label("📊 Mis Cuentas (" + portafolioPersonal.getComponentes().size() + ")");
        misCuentasTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        misCuentasTitle.setStyle("-fx-text-fill: #1e3c72;");

        // Lista de todas las cuentas del usuario
        VBox cuentasListBox = new VBox(15);
        int numeroCuenta = 1;
        for (ComponenteBancario comp : portafolioPersonal.getComponentes()) {
            if (comp instanceof Cuenta) {
                Cuenta c = (Cuenta) comp;
                VBox cuentaPanel = createCuentaPanel(c, numeroCuenta++);
                cuentasListBox.getChildren().add(cuentaPanel);
            }
        }

        // Panel de operaciones conjuntas
        VBox operacionesConjuntasPanel = new VBox(15);
        operacionesConjuntasPanel.setPadding(new Insets(20));
        operacionesConjuntasPanel.setStyle("-fx-background-color: #fff3cd; -fx-border-color: #ffc107; " +
                                          "-fx-border-radius: 10; -fx-background-radius: 10;");

        Label opConjuntasTitle = new Label("⚡ Operaciones Conjuntas en Todas las Cuentas");
        opConjuntasTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        opConjuntasTitle.setStyle("-fx-text-fill: #856404;");

        Label opConjuntasDesc = new Label(
            "Realice depositos o retiros simultaneos en todas sus cuentas del portafolio");
        opConjuntasDesc.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        opConjuntasDesc.setStyle("-fx-text-fill: #856404;");
        opConjuntasDesc.setWrapText(true);

        HBox botonesConjuntosBox = new HBox(15);
        botonesConjuntosBox.setAlignment(Pos.CENTER);

        Button depositarConjuntoBtn = createStyledButton("💰 Depositar en Todas", "#28a745");
        depositarConjuntoBtn.setPrefWidth(220);
        depositarConjuntoBtn.setOnAction(e -> {
            realizarOperacionConjunta(portafolioPersonal, true, dashboardStage);
        });

        Button retirarConjuntoBtn = createStyledButton("💸 Retirar de Todas", "#dc3545");
        retirarConjuntoBtn.setPrefWidth(220);
        retirarConjuntoBtn.setOnAction(e -> {
            realizarOperacionConjunta(portafolioPersonal, false, dashboardStage);
        });

        Button exportarPortafolioBtn = createStyledButton("📄 Exportar Portafolio", "#007bff");
        exportarPortafolioBtn.setPrefWidth(220);
        exportarPortafolioBtn.setOnAction(e -> {
            GeneradorTXT.exportarPortafolio(portafolioPersonal, cuenta.getCliente() + "_portafolio.txt");
            log("📄 Portafolio personal exportado: " + cuenta.getCliente() + "_portafolio.txt");
            showAlert("Exportacion Exitosa",
                     "Su portafolio ha sido exportado a:\n" + cuenta.getCliente() + "_portafolio.txt",
                     Alert.AlertType.INFORMATION);
        });

        botonesConjuntosBox.getChildren().addAll(depositarConjuntoBtn, retirarConjuntoBtn, exportarPortafolioBtn);

        operacionesConjuntasPanel.getChildren().addAll(opConjuntasTitle, opConjuntasDesc, botonesConjuntosBox);

        centerPanel.getChildren().addAll(saldoGlobalCard, misCuentasTitle, cuentasListBox, operacionesConjuntasPanel);
        scrollPane.setContent(centerPanel);
        root.setCenter(scrollPane);

        Scene scene = new Scene(root, 850, 650);
        dashboardStage.setScene(scene);
        dashboardStage.show();
    }

    /**
     * Crea un panel para mostrar una cuenta individual dentro del portafolio.
     * @param cuenta La cuenta a mostrar.
     * @param numero El numero de cuenta en la lista.
     * @return El panel de la cuenta.
     */
    private VBox createCuentaPanel(Cuenta cuenta, int numero) {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(15));
        panel.setStyle("-fx-background-color: white; -fx-border-color: #dee2e6; " +
                      "-fx-border-radius: 8; -fx-background-radius: 8; " +
                      "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2);");

        HBox headerBox = new HBox(10);
        headerBox.setAlignment(Pos.CENTER_LEFT);

        Label numeroLabel = new Label("#" + numero);
        numeroLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        numeroLabel.setStyle("-fx-text-fill: #6c757d;");

        Label tipoLabel = new Label("Cuenta " + (numero == 1 ? "Ahorro" : numero == 2 ? "Nomina" : "Inversion"));
        tipoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        tipoLabel.setStyle("-fx-text-fill: #1e3c72;");

        Label estadoBadge = new Label(cuenta.getEstado().getEstado());
        estadoBadge.setFont(Font.font("Arial", FontWeight.BOLD, 11));
        estadoBadge.setPadding(new Insets(3, 10, 3, 10));
        estadoBadge.setStyle("-fx-background-color: " + getEstadoColor(cuenta.getEstado().getEstado()) +
                            "; -fx-text-fill: white; -fx-background-radius: 12;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

        headerBox.getChildren().addAll(numeroLabel, tipoLabel, spacer, estadoBadge);

        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(20);
        infoGrid.setVgap(8);
        infoGrid.setPadding(new Insets(10, 0, 0, 0));

        addDetailRow(infoGrid, "💰 Saldo:", String.format("$%,.2f", cuenta.getSaldo()), 0);
        addDetailRow(infoGrid, "📈 Tipo de Interes:",
            cuenta.getEstrategiaInteres().getClass().getSimpleName().replace("Interes", ""), 1);
        addDetailRow(infoGrid, "📅 Antiguedad:", cuenta.getMesesAntiguedad() + " meses", 2);

        HBox botonesBox = new HBox(10);
        botonesBox.setAlignment(Pos.CENTER);
        botonesBox.setPadding(new Insets(10, 0, 0, 0));

        Button depositarBtn = new Button("💵 Depositar");
        depositarBtn.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; " +
                             "-fx-font-size: 11px; -fx-padding: 6 15; -fx-background-radius: 5;");
        depositarBtn.setOnAction(e -> {
            realizarDepositoSimple(cuenta);
        });

        Button retirarBtn = new Button("💸 Retirar");
        retirarBtn.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; " +
                           "-fx-font-size: 11px; -fx-padding: 6 15; -fx-background-radius: 5;");
        retirarBtn.setOnAction(e -> {
            realizarRetiroSimple(cuenta);
        });

        Button exportarBtn = new Button("📄 Exportar");
        exportarBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; " +
                            "-fx-font-size: 11px; -fx-padding: 6 15; -fx-background-radius: 5;");
        exportarBtn.setOnAction(e -> {
            GeneradorTXT.exportarCuenta(cuenta, cuenta.getCliente() + "_cuenta" + numero + ".txt");
            log("📄 Cuenta #" + numero + " exportada");
            showAlert("Exportacion Exitosa", "Archivo generado correctamente", Alert.AlertType.INFORMATION);
        });

        botonesBox.getChildren().addAll(depositarBtn, retirarBtn, exportarBtn);

        panel.getChildren().addAll(headerBox, infoGrid, botonesBox);
        return panel;
    }

    /**
     * Realiza una operacion conjunta en todas las cuentas del portafolio.
     * @param portafolio El portafolio personal del usuario.
     * @param esDeposito True si es deposito, false si es retiro.
     * @param stage El stage del dashboard para actualizar.
     */
    private void realizarOperacionConjunta(Portafolio portafolio, boolean esDeposito, Stage stage) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(esDeposito ? "Deposito Conjunto" : "Retiro Conjunto");
        dialog.setHeaderText("Operacion en TODAS las cuentas del portafolio");
        dialog.setContentText("Monto a " + (esDeposito ? "depositar" : "retirar") + " en cada cuenta:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(monto -> {
            try {
                double cantidad = Double.parseDouble(monto);
                if (cantidad <= 0) {
                    showAlert("Error", "El monto debe ser positivo", Alert.AlertType.ERROR);
                    return;
                }

                if (esDeposito) {
                    portafolio.depositar(cantidad);
                    log("✅ Deposito conjunto de $" + cantidad + " en " +
                        portafolio.getComponentes().size() + " cuentas");
                    showAlert("✅ Operacion Exitosa",
                             String.format("Se depositaron $%,.2f en cada una de sus %d cuentas.\n" +
                                         "Total depositado: $%,.2f",
                                         cantidad, portafolio.getComponentes().size(),
                                         cantidad * portafolio.getComponentes().size()),
                             Alert.AlertType.INFORMATION);
                } else {
                    portafolio.retirar(cantidad);
                    log("✅ Retiro conjunto de $" + cantidad + " de " +
                        portafolio.getComponentes().size() + " cuentas");
                    showAlert("✅ Operacion Exitosa",
                             String.format("Se retiraron $%,.2f de cada una de sus %d cuentas.\n" +
                                         "Total retirado: $%,.2f",
                                         cantidad, portafolio.getComponentes().size(),
                                         cantidad * portafolio.getComponentes().size()),
                             Alert.AlertType.INFORMATION);
                }

                // Cerrar y reabrir el dashboard para actualizar
                stage.close();
                ComponenteBancario primerComponente = portafolio.getComponentes().get(0);
                if (primerComponente instanceof Cuenta) {
                    showCuentaDashboard((Cuenta) primerComponente);
                }

            } catch (NumberFormatException e) {
                showAlert("Error", "Ingrese un numero valido", Alert.AlertType.ERROR);
            } catch (Exception e) {
                showAlert("Error", "Error en la operacion: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }

    /**
     * Realiza un deposito simple en una cuenta específica.
     * @param cuenta La cuenta donde depositar.
     */
    private void realizarDepositoSimple(Cuenta cuenta) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Depositar");
        dialog.setHeaderText("Deposito en cuenta de " + cuenta.getCliente());
        dialog.setContentText("Monto a depositar:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(monto -> {
            try {
                double cantidad = Double.parseDouble(monto);
                if (cantidad <= 0) throw new EntradaInvalida("El monto debe ser positivo");

                cuenta.depositar(cantidad);
                log("✅ Deposito: $" + cantidad + " en cuenta de " + cuenta.getCliente());
                showAlert("Exito", "Deposito de $" + String.format("%,.2f", cantidad) +
                         " realizado correctamente", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }

    /**
     * Realiza un retiro simple de una cuenta específica.
     * @param cuenta La cuenta de donde retirar.
     */
    private void realizarRetiroSimple(Cuenta cuenta) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Retirar");
        dialog.setHeaderText("Retiro de cuenta de " + cuenta.getCliente());
        dialog.setContentText("Monto a retirar:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(monto -> {
            try {
                double cantidad = Double.parseDouble(monto);
                if (cantidad <= 0) throw new EntradaInvalida("El monto debe ser positivo");

                cuenta.retirar(cantidad);
                log("✅ Retiro: $" + cantidad + " de cuenta de " + cuenta.getCliente());
                showAlert("Exito", "Retiro de $" + String.format("%,.2f", cantidad) +
                         " realizado correctamente", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        });
    }

    /**
     * Crea una tarjeta de informacion.
     * @param titulo El titulo de la tarjeta.
     * @param valor El valor a mostrar.
     * @param color El color del borde.
     * @return La tarjeta como VBox.
     */
    private VBox createInfoCard(String titulo, String valor, String color) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setAlignment(Pos.CENTER);
        card.setPrefWidth(200);
        card.setPrefHeight(120);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; " +
                      "-fx-border-color: " + color + "; -fx-border-width: 0 0 3 0; " +
                      "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");

        Label titleLabel = new Label(titulo);
        titleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        titleLabel.setStyle("-fx-text-fill: #6c757d;");

        Label valueLabel = new Label(valor);
        valueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        valueLabel.setStyle("-fx-text-fill: " + color + ";");

        card.getChildren().addAll(titleLabel, valueLabel);
        return card;
    }

    /**
     * Obtiene el color segun el estado.
     * @param estado El estado de la cuenta.
     * @return El color hexadecimal.
     */
    private String getEstadoColor(String estado) {
        switch(estado) {
            case "Activa": return "#28a745";
            case "Sobregirada": return "#ffc107";
            case "Congelada": return "#17a2b8";
            case "Cerrada": return "#6c757d";
            default: return "#007bff";
        }
    }

    /**
     * Agrega una fila de detalle al grid.
     * @param grid El grid donde agregar.
     * @param label La etiqueta.
     * @param value El valor.
     * @param row La fila.
     */
    private void addDetailRow(GridPane grid, String label, String value, int row) {
        Label lblLabel = new Label(label);
        lblLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lblLabel.setStyle("-fx-text-fill: #495057;");

        Label lblValue = new Label(value);
        lblValue.setFont(Font.font("Arial", FontWeight.NORMAL, 12));
        lblValue.setStyle("-fx-text-fill: #212529;");

        grid.add(lblLabel, 0, row);
        grid.add(lblValue, 1, row);
    }

    /**
     * Crea un boton estilizado.
     * @param text El texto del boton.
     * @param color El color del boton.
     * @return El boton creado.
     */
    private Button createStyledButton(String text, String color) {
        Button btn = new Button(text);
        btn.setPrefWidth(170);
        btn.setPrefHeight(40);
        btn.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; " +
                     "-fx-font-size: 13px; -fx-font-weight: bold; -fx-background-radius: 5;");
        btn.setOnMouseEntered(e -> btn.setOpacity(0.8));
        btn.setOnMouseExited(e -> btn.setOpacity(1.0));
        return btn;
    }

    /**
     * Crea el panel del proceso mensual.
     * @return El panel del proceso mensual como un VBox.
     */
    private VBox createProcesoMensualPanel() {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(30));
        panel.setStyle("-fx-background-color: white;");

        Label title = new Label("📅 Proceso de Fin de Mes");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: #1e3c72;");

        Label subtitle = new Label("Ejecuta el cierre mensual para todas las cuentas del sistema");
        subtitle.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        subtitle.setStyle("-fx-text-fill: #6c757d;");

        // Panel de informacion
        VBox infoBox = new VBox(10);
        infoBox.setPadding(new Insets(20));
        infoBox.setStyle("-fx-background-color: #e3f2fd; -fx-background-radius: 10; -fx-border-color: #2196F3; -fx-border-radius: 10;");

        Label infoTitle = new Label("ℹ️ Operaciones del Proceso:");
        infoTitle.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        VBox operacionesList = new VBox(5);
        operacionesList.setPadding(new Insets(10, 0, 0, 20));
        operacionesList.getChildren().addAll(
            new Label("1. Actualizacion de antiguedad de cuentas"),
            new Label("2. Revision y aplicacion de cargos por sobregiro"),
            new Label("3. Calculo de intereses segun estrategia"),
            new Label("4. Generacion de reportes mensuales en TXT"),
            new Label("5. Notificacion a clientes sobre cambios")
        );

        infoBox.getChildren().addAll(infoTitle, operacionesList);

        // Area de resultados
        TextArea procesoInfo = new TextArea();
        procesoInfo.setEditable(false);
        procesoInfo.setPrefHeight(250);
        procesoInfo.setStyle("-fx-font-family: 'Consolas', 'Courier New'; -fx-font-size: 11px; " +
                            "-fx-control-inner-background: #f8f9fa; -fx-text-fill: #212529;");
        procesoInfo.setText("Esperando ejecucion del proceso mensual...\n\n" +
                           "El sistema procesara " + cuentas.size() + " cuenta(s) activas.");

        // Boton de ejecutar
        Button ejecutarBtn = new Button("▶️ EJECUTAR PROCESO MENSUAL");
        ejecutarBtn.setPrefWidth(350);
        ejecutarBtn.setPrefHeight(50);
        ejecutarBtn.setStyle("-fx-background-color: linear-gradient(to right, #f44336, #e91e63); " +
                            "-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; " +
                            "-fx-background-radius: 5; -fx-cursor: hand;");

        ejecutarBtn.setOnMouseEntered(e -> ejecutarBtn.setStyle(
            "-fx-background-color: linear-gradient(to right, #d32f2f, #c2185b); " +
            "-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; " +
            "-fx-background-radius: 5; -fx-cursor: hand;"));
        ejecutarBtn.setOnMouseExited(e -> ejecutarBtn.setStyle(
            "-fx-background-color: linear-gradient(to right, #f44336, #e91e63); " +
            "-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; " +
            "-fx-background-radius: 5; -fx-cursor: hand;"));

        ejecutarBtn.setOnAction(e -> {
            procesoInfo.setText("═══════════════════════════════════════════════════════════════\n");
            procesoInfo.appendText("          INICIANDO PROCESO MENSUAL - " +
                                  java.time.LocalDateTime.now().toString().substring(0, 19) + "\n");
            procesoInfo.appendText("═══════════════════════════════════════════════════════════════\n\n");

            log("📅 Iniciando proceso mensual para " + cuentas.size() + " cuentas");

            // Ejecutar proceso
            procesoMensual.ejecutarProcesoMensual();

            // Mostrar resultados
            procesoInfo.appendText("✅ Proceso completado exitosamente\n\n");
            procesoInfo.appendText("RESUMEN DE OPERACIONES:\n");
            procesoInfo.appendText("─────────────────────────────────────────────────────────────\n\n");

            int contador = 1;
            for(Cuenta c : cuentas) {
                procesoInfo.appendText(String.format("%d. %-20s | Saldo: $%,12.2f | Estado: %-12s\n",
                    contador++,
                    c.getCliente(),
                    c.getSaldo(),
                    c.getEstado().getEstado()));
                procesoInfo.appendText(String.format("   Antiguedad: %d meses | Tipo: %s\n",
                    c.getMesesAntiguedad(),
                    c.getEstrategiaInteres().getClass().getSimpleName().replace("Interes", "")));
                procesoInfo.appendText(String.format("   📄 Reporte generado: %s_reporte.txt\n\n",
                    c.getCliente()));
            }

            procesoInfo.appendText("═══════════════════════════════════════════════════════════════\n");
            procesoInfo.appendText("              PROCESO FINALIZADO CORRECTAMENTE\n");
            procesoInfo.appendText("═══════════════════════════════════════════════════════════════\n");

            log("📅 Proceso mensual completado exitosamente");
            showAlert("✅ Proceso Completado",
                     "El proceso mensual ha sido ejecutado correctamente.\n" +
                     "Se procesaron " + cuentas.size() + " cuenta(s).",
                     Alert.AlertType.INFORMATION);
        });

        VBox btnContainer = new VBox(ejecutarBtn);
        btnContainer.setAlignment(Pos.CENTER);

        panel.getChildren().addAll(title, subtitle, infoBox,
                                   new Label("Resultado de la Ejecucion:"),
                                   procesoInfo, btnContainer);
        return panel;
    }

    /**
     * Crea el panel para crear una nueva cuenta.
     * @return El panel de creacion de cuenta como un VBox.
     */
    private VBox createCrearCuentaPanel() {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(30));
        panel.setAlignment(Pos.TOP_CENTER);
        panel.setStyle("-fx-background-color: white; -fx-background-radius: 10;");

        Label titleLabel = new Label("➕ Crear Nueva Cuenta");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setStyle("-fx-text-fill: #1e3c72;");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(15);
        form.setAlignment(Pos.CENTER);

        TextField nombreField = new TextField();
        nombreField.setPromptText("Ej: MARIA");
        nombreField.setPrefWidth(250);

        TextField saldoField = new TextField();
        saldoField.setPromptText("Ej: 5000.00");
        saldoField.setPrefWidth(250);

        PasswordField nipField = new PasswordField();
        nipField.setPromptText("4 digitos diferentes");
        nipField.setPrefWidth(250);

        ComboBox<String> tipoCombo = new ComboBox<>();
        tipoCombo.getItems().addAll("Mensual", "Anual", "Premium");
        tipoCombo.setValue("Mensual");
        tipoCombo.setPrefWidth(250);

        Label nombreLabel = new Label("Nombre del cliente:");
        nombreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Label saldoLabel = new Label("Saldo inicial:");
        saldoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Label nipLabel = new Label("NIP (4 digitos):");
        nipLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        Label tipoLabel = new Label("Tipo de interes:");
        tipoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        form.add(nombreLabel, 0, 0);
        form.add(nombreField, 1, 0);
        form.add(saldoLabel, 0, 1);
        form.add(saldoField, 1, 1);
        form.add(nipLabel, 0, 2);
        form.add(nipField, 1, 2);
        form.add(tipoLabel, 0, 3);
        form.add(tipoCombo, 1, 3);

        Button crearBtn = new Button("✅ Crear Cuenta");
        crearBtn.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; " +
                          "-fx-font-size: 14px; -fx-padding: 12 40; -fx-background-radius: 5; " +
                          "-fx-cursor: hand;");
        crearBtn.setOnAction(e -> {
            try {
                StrategyInteres interes;
                switch(tipoCombo.getValue()) {
                    case "Anual": interes = new InteresAnual(); break;
                    case "Premium": interes = new InteresPremium(); break;
                    default: interes = new InteresMensual(); break;
                }
                Cuenta nuevaCuenta = new Cuenta(nombreField.getText().toUpperCase(),
                                                 Double.parseDouble(saldoField.getText()),
                                                 interes, new EstadoActiva(), nipField.getText());
                cuentas.add(nuevaCuenta);
                portafolio.agregar(nuevaCuenta);
                procesoMensual.agregarCuenta(nuevaCuenta);
                log("✅ Cuenta creada: " + nuevaCuenta.getCliente());
                showAlert("Exito", "Cuenta creada correctamente para " + nuevaCuenta.getCliente(), Alert.AlertType.INFORMATION);

                // Limpiar campos
                nombreField.clear();
                saldoField.clear();
                nipField.clear();
                tipoCombo.setValue("Mensual");
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage(), Alert.AlertType.ERROR);
                log("❌ Error al crear cuenta: " + ex.getMessage());
            }
        });

        panel.getChildren().addAll(titleLabel, new Label(" "), form, new Label(" "), crearBtn);
        return panel;
    }

    /**
     * Crea el panel de estadisticas del sistema.
     * @return El panel de estadisticas como un BorderPane.
     */
    private BorderPane createEstadisticasPanel() {
        BorderPane panel = new BorderPane();
        panel.setPadding(new Insets(20));
        panel.setStyle("-fx-background-color: white;");

        VBox statsContainer = new VBox(20);
        statsContainer.setPadding(new Insets(20));

        Label title = new Label("📊 Estadisticas del Sistema PumaBank");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        title.setStyle("-fx-text-fill: #1e3c72;");

        // Panel de resumen general
        GridPane resumenGrid = new GridPane();
        resumenGrid.setHgap(20);
        resumenGrid.setVgap(15);
        resumenGrid.setPadding(new Insets(20));
        resumenGrid.setStyle("-fx-background-color: #e9ecef; -fx-background-radius: 10; -fx-padding: 20;");

        // Boton de actualizar
        Button actualizarBtn = new Button("🔄 Actualizar Estadisticas");
        actualizarBtn.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; " +
                               "-fx-font-size: 12px; -fx-padding: 8 20; -fx-background-radius: 5;");

        TextArea detallesArea = new TextArea();
        detallesArea.setEditable(false);
        detallesArea.setPrefHeight(300);
        detallesArea.setFont(Font.font("Courier New", 12));
        detallesArea.setStyle("-fx-control-inner-background: #f8f9fa;");

        actualizarBtn.setOnAction(e -> {
            // Calcular estadisticas
            int totalCuentas = cuentas.size();
            int cuentasActivas = 0;
            int cuentasCongeladas = 0;
            int cuentasSobregiradas = 0;
            int cuentasCerradas = 0;
            double saldoTotal = 0;
            double saldoPromedio = 0;
            double saldoMaximo = 0;
            double saldoMinimo = Double.MAX_VALUE;
            String clienteMax = "";
            String clienteMin = "";

            int cuentasMensual = 0;
            int cuentasAnual = 0;
            int cuentasPremium = 0;

            for (Cuenta c : cuentas) {
                saldoTotal += c.getSaldo();

                if (c.getSaldo() > saldoMaximo) {
                    saldoMaximo = c.getSaldo();
                    clienteMax = c.getCliente();
                }
                if (c.getSaldo() < saldoMinimo) {
                    saldoMinimo = c.getSaldo();
                    clienteMin = c.getCliente();
                }

                String estado = c.getEstado().getEstado();
                if (estado.equals("Activa")) cuentasActivas++;
                else if (estado.equals("Congelada")) cuentasCongeladas++;
                else if (estado.equals("Sobregirada")) cuentasSobregiradas++;
                else if (estado.equals("Cerrada")) cuentasCerradas++;

                if (c.getEstrategiaInteres() instanceof InteresMensual) cuentasMensual++;
                else if (c.getEstrategiaInteres() instanceof InteresAnual) cuentasAnual++;
                else if (c.getEstrategiaInteres() instanceof InteresPremium) cuentasPremium++;
            }

            if (totalCuentas > 0) {
                saldoPromedio = saldoTotal / totalCuentas;
            }

            // Actualizar grid
            resumenGrid.getChildren().clear();

            addStatItem(resumenGrid, "💼 Total de Cuentas:", String.valueOf(totalCuentas), 0);
            addStatItem(resumenGrid, "💰 Saldo Total:", String.format("$%,.2f", saldoTotal), 1);
            addStatItem(resumenGrid, "📊 Saldo Promedio:", String.format("$%,.2f", saldoPromedio), 2);
            addStatItem(resumenGrid, "📈 Saldo Maximo:", String.format("$%,.2f (%s)", saldoMaximo, clienteMax), 3);
            addStatItem(resumenGrid, "📉 Saldo Minimo:", String.format("$%,.2f (%s)", saldoMinimo, clienteMin), 4);

            // Detalles por estado
            StringBuilder detalles = new StringBuilder();
            detalles.append("═══════════════════════════════════════════════════════════════════\n");
            detalles.append("                   RESUMEN POR ESTADO DE CUENTA\n");
            detalles.append("═══════════════════════════════════════════════════════════════════\n\n");
            detalles.append(String.format("  ✅ Cuentas Activas:      %3d (%.1f%%)\n", cuentasActivas, (cuentasActivas*100.0/totalCuentas)));
            detalles.append(String.format("  ⚠️  Cuentas Sobregiradas: %3d (%.1f%%)\n", cuentasSobregiradas, (cuentasSobregiradas*100.0/totalCuentas)));
            detalles.append(String.format("  ❄️  Cuentas Congeladas:   %3d (%.1f%%)\n", cuentasCongeladas, (cuentasCongeladas*100.0/totalCuentas)));
            detalles.append(String.format("  🔒 Cuentas Cerradas:     %3d (%.1f%%)\n", cuentasCerradas, (cuentasCerradas*100.0/totalCuentas)));

            detalles.append("\n═══════════════════════════════════════════════════════════════════\n");
            detalles.append("                   DISTRIBUCION POR TIPO DE INTERES\n");
            detalles.append("═══════════════════════════════════════════════════════════════════\n\n");
            detalles.append(String.format("  📅 Interes Mensual:  %3d cuentas (%.1f%%)\n", cuentasMensual, (cuentasMensual*100.0/totalCuentas)));
            detalles.append(String.format("  📆 Interes Anual:    %3d cuentas (%.1f%%)\n", cuentasAnual, (cuentasAnual*100.0/totalCuentas)));
            detalles.append(String.format("  ⭐ Interes Premium:  %3d cuentas (%.1f%%)\n", cuentasPremium, (cuentasPremium*100.0/totalCuentas)));

            detalles.append("\n═══════════════════════════════════════════════════════════════════\n");
            detalles.append("                   DETALLE DE TODAS LAS CUENTAS\n");
            detalles.append("═══════════════════════════════════════════════════════════════════\n\n");

            for (Cuenta c : cuentas) {
                detalles.append(String.format("  👤 %-20s | Saldo: $%,12.2f | Estado: %-12s | Tipo: %-15s\n",
                    c.getCliente(),
                    c.getSaldo(),
                    c.getEstado().getEstado(),
                    c.getEstrategiaInteres().getClass().getSimpleName().replace("Interes", "")));
            }

            detalles.append("\n═══════════════════════════════════════════════════════════════════\n");

            detallesArea.setText(detalles.toString());
            log("📊 Estadisticas actualizadas");
        });

        // Ejecutar calculo inicial
        actualizarBtn.fire();

        statsContainer.getChildren().addAll(title, resumenGrid, actualizarBtn,
                                            new Label("Detalles:"), detallesArea);

        panel.setCenter(statsContainer);
        return panel;
    }

    /**
     * Agrega un item de estadistica al grid.
     * @param grid El grid donde agregar.
     * @param label La etiqueta del item.
     * @param value El valor del item.
     * @param row La fila en el grid.
     */
    private void addStatItem(GridPane grid, String label, String value, int row) {
        Label lblLabel = new Label(label);
        lblLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        lblLabel.setStyle("-fx-text-fill: #495057;");

        Label lblValue = new Label(value);
        lblValue.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
        lblValue.setStyle("-fx-text-fill: #212529;");

        grid.add(lblLabel, 0, row);
        grid.add(lblValue, 1, row);
    }

    /**
     * Muestra una alerta en la interfaz.
     * @param title El titulo de la alerta.
     * @param content El contenido de la alerta.
     * @param type El tipo de alerta.
     */
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Registra un mensaje en el area de log.
     * @param message El mensaje a registrar.
     */
    private void log(String message) {
        if (logArea != null) {
            logArea.appendText("[" + java.time.LocalTime.now().toString().substring(0, 8) + "] " + message + "\n");
        }
    }

    /**
     * Clase interna para personalizar la celda del arbol de componentes bancarios.
     */
    private static class ComponenteBancarioTreeCell extends TreeCell<ComponenteBancario> {
        /**
         * Actualiza el contenido de la celda.
         * @param item El componente bancario a mostrar.
         * @param empty Si la celda esta vacia.
         */
        @Override
        protected void updateItem(ComponenteBancario item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                HBox hbox = new HBox(5);
                hbox.setAlignment(Pos.CENTER_LEFT);
                Text icon = new Text(item instanceof Portafolio ? "💼" : "👤");
                hbox.getChildren().addAll(icon, new Label(item.getCliente()));
                setGraphic(hbox);
                setText(null);
            }
        }
    }
}