package main.java.software.cafeteria.controladores;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.java.software.cafeteria.delegado.ProductoObservable;

public class InventarioController {

	private ManejadorEscenarios manejador;

	private Stage stage;

	@FXML
	private TableView<ProductoObservable> tabla;

	@FXML
	private TableColumn<ProductoObservable, String> producto;

	@FXML
	private TableColumn<ProductoObservable, String> tipoProducto;

	@FXML
	private TableColumn<ProductoObservable, String> iva;

	@FXML
	private TableColumn<ProductoObservable, String> precio;

	@FXML
	private TableColumn<ProductoObservable, String> costo;

	@FXML
	private TableColumn<ProductoObservable, String> cantidad;

	@FXML
	private TableColumn<ProductoObservable, String> codigoBarras;

	@FXML
	private TableColumn<ProductoObservable, String> presentacion;
	@FXML
	private TableColumn<ProductoObservable, String> empresa;

	@FXML
	private TableColumn<ProductoObservable, Void> btnEliminar;
	@FXML
	private TableColumn<ProductoObservable, Void> btnModificar;

	@FXML
	private ComboBox<String> comboActualizarTabla;

	@FXML
	private TextField busquedaProducto;

	@FXML
	private Button btn_agregar;

	@FXML
	private Button btn_regresar;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	public void initialize() {

		producto.setCellValueFactory(new Callback<CellDataFeatures<ProductoObservable, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<ProductoObservable, String> nomb) {
				return nomb.getValue().getNombre();
			}
		});
		tipoProducto.setCellValueFactory(new Callback<CellDataFeatures<ProductoObservable, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<ProductoObservable, String> tipo) {
				return tipo.getValue().getTipoProducto();
			}
		});
		iva.setCellValueFactory(new Callback<CellDataFeatures<ProductoObservable, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<ProductoObservable, String> iva) {
				return iva.getValue().getIva();
			}
		});
		precio.setCellValueFactory(
				new Callback<CellDataFeatures<ProductoObservable, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<ProductoObservable, String> pre) {
						return pre.getValue().getPrecio();
					}
				});
		costo.setCellValueFactory(new Callback<CellDataFeatures<ProductoObservable, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<ProductoObservable, String> cos) {
				return cos.getValue().getCosto();
			}
		});
		cantidad.setCellValueFactory(new Callback<CellDataFeatures<ProductoObservable, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<ProductoObservable, String> can) {
				return can.getValue().getCantidad();
			}
		});
		codigoBarras.setCellValueFactory(new Callback<CellDataFeatures<ProductoObservable, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<ProductoObservable, String> codig) {
				return codig.getValue().getCodigoBarras();
			}
		});
		presentacion.setCellValueFactory(new Callback<CellDataFeatures<ProductoObservable, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<ProductoObservable, String> present) {
				return present.getValue().getPresentacion();
			}
		});
		empresa.setCellValueFactory(new Callback<CellDataFeatures<ProductoObservable, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<ProductoObservable, String> present) {
				return present.getValue().getEmpresa();
			}
		});
		btnEliminar = new TableColumn("");
		btnModificar = new TableColumn("");

		ImageView image = new ImageView("file:src/main/java/software/cafeteria/images/agregarAlInventario.png");
		btn_agregar.setGraphic(image);
		btn_regresar.setGraphic(new ImageView("file:src/main/java/software/cafeteria/images/regresar.png"));
		botones();
	}

	@FXML
	public void regresar() {
		manejador.ventanaPrincipal();
		stage.close();
	}

	public void botones() {

		Callback<TableColumn<ProductoObservable, Void>, TableCell<ProductoObservable, Void>> cellFactory1 = new Callback<TableColumn<ProductoObservable, Void>, TableCell<ProductoObservable, Void>>() {
			public TableCell<ProductoObservable, Void> call(final TableColumn<ProductoObservable, Void> param) {
				final TableCell<ProductoObservable, Void> cell = new TableCell<ProductoObservable, Void>() {
					final ImageView imageView = new ImageView(
							new Image("file:src/main/java/software/cafeteria/images/eliminarCarrito.png"));
					private final Button btn = new Button("", imageView);

					{
						btn.setOnAction(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent event) {

								boolean res = manejador.eliminarProducto(getIndex());

								if (res) {
									Alert alert = new Alert(AlertType.INFORMATION, "El producto se elimino con exito",
											ButtonType.OK);
									alert.showAndWait();

								} else {
									Alert alert = new Alert(AlertType.ERROR, "El producto no se pudo eliminar",
											ButtonType.OK);
									alert.showAndWait();
								}

							}
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};

		btnEliminar.setCellFactory(cellFactory1);

		tabla.getColumns().add(btnEliminar);

		Callback<TableColumn<ProductoObservable, Void>, TableCell<ProductoObservable, Void>> cellFactory2 = new Callback<TableColumn<ProductoObservable, Void>, TableCell<ProductoObservable, Void>>() {
			public TableCell<ProductoObservable, Void> call(final TableColumn<ProductoObservable, Void> param) {
				final TableCell<ProductoObservable, Void> cell = new TableCell<ProductoObservable, Void>() {

					final ImageView imageView = new ImageView(
							new Image("file:src/main/java/software/cafeteria/images/editarProducto.png"));

					private final Button btn = new Button("", imageView);
					{
						btn.setOnAction(new EventHandler<ActionEvent>() {
							public void handle(ActionEvent event) {
								manejador.ventanaModificarProducto(getIndex());
								tabla.setItems(manejador.actualizarTablaTipo(manejador.getTipoSeleccionado()));
							}
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};
		btnModificar.setCellFactory(cellFactory2);

		tabla.getColumns().add(btnModificar);
	}

	@FXML
	void agregarProducto() {

		manejador.ventanaAgregarProducto();

	}

	@FXML
	public void buscarProductoNombre() {
		ObservableList<ProductoObservable> lista = manejador.buscarProductoNombre(busquedaProducto.getText());

		tabla.setItems(lista);
	}

	@FXML
	public void actualizarTablaTipo() {
		manejador.setTipoSeleccionado(comboActualizarTabla.getSelectionModel().getSelectedItem());
		tabla.setItems(manejador.actualizarTablaTipo(manejador.getTipoSeleccionado()));
	}

	public void actualizarTabla() {
		tabla.setItems(null);
		tabla.setItems(manejador.listarProductos());
		comboActualizarTabla.setItems(manejador.listarTipos1());
		comboActualizarTabla.getSelectionModel().select(manejador.getTipoSeleccionado());
	}

	public void setManejador(ManejadorEscenarios manejador) {

		this.manejador = manejador;
		actualizarTabla();
	}

	public ManejadorEscenarios getManejador() {

		return manejador;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

}
