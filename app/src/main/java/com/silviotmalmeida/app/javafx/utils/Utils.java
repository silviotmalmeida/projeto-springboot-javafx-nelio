package com.silviotmalmeida.app.javafx.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.silviotmalmeida.app.entities.Department;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.util.StringConverter;

// classe utilitária geral
public class Utils {

    // método que retorna o stage onde o evento foi disparado
    public static Stage currentStage(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    // método que converte uma string para inteiro, retornando null em caso de
    // exceção
    public static Integer tryParseToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // método que converte uma string para long, retornando null em caso de
    // exceção
    public static Long tryParseToLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // método que converte uma string para double, retornando null em caso de
    // exceção
    public static Double tryParseToDouble(String str) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // método que formata a exibição da data na tabela de dados
    public static <T> void formatTableColumnInstant(TableColumn<T, Instant> tableColumn, String format) {
        tableColumn.setCellFactory(column -> {
            TableCell<T, Instant> cell = new TableCell<T, Instant>() {

                private DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format)
                        .withZone(ZoneId.systemDefault());;

                @Override
                protected void updateItem(Instant item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(formatter.format(item));
                    }
                }
            };
            return cell;
        });
    }

    // método que formata a exibição do salário na tabela de dados
    public static <T> void formatTableColumnDouble(TableColumn<T, Double> tableColumn, int decimalPlaces) {
        tableColumn.setCellFactory(column -> {
            TableCell<T, Double> cell = new TableCell<T, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        Locale.setDefault(Locale.US);
                        setText(String.format("%." + decimalPlaces + "f", item));
                    }
                }
            };
            return cell;
        });
    }

    // método que formata a exibição do departamento na tabela de dados
    public static <T> void formatTableColumnDepartment(TableColumn<T, Department> tableColumn) {
        tableColumn.setCellFactory(column -> {
            TableCell<T, Department> cell = new TableCell<T, Department>() {
                @Override
                protected void updateItem(Department item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item.getName());
                    }
                }
            };
            return cell;
        });
    }

    // método que formata a exibição do datePicker no formulário
    public static void formatDatePicker(DatePicker datePicker, String format) {
        datePicker.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(format);
            {
                datePicker.setPromptText(format.toLowerCase());
            }

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
    }
}