package com.yhlo.oa.util;

import com.yhlo.oa.entity.BaseEntity;
import com.yhlo.oa.entity.It_MaraVO;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
/**
 * @author cy
 * @ClassName: ModuleUtils
 * @Description:
 * @date 2022/6/115:53
 */
public class ModuleUtils {

//====================================================对tableView代码提取===========================================

    /**
     * 根据columnList(表头集合)、dataList(数据集合)初始化tableView
     *
     * @param tableView  tableView对象
     * @param columnList 表格字段集合
     * @param dataList   表格数据集合
     */
    public static void createTable(TableView tableView, List<TableColumn> columnList, List dataList) {
        //AssertUtils.assertTrue(Objects.isNull(tableView), "Component is Null!");
        // 设置表格可编辑
        tableView.setEditable(true);
        // 设置多选
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // 去掉空白多于列
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // 设置表头
        tableView.getColumns().addAll(columnList);
        // 更新表格数据
        ObservableList<It_MaraVO> data = FXCollections.observableArrayList(dataList);
        tableView.setItems(data);
        // 使tableView随窗口变化而变化

    }


    /**
     * 方法描述：根据columnList(表头集合)、dataList(数据集合)初始化tableView
     * 注意：设置TableView的宽高去适应界面的大小行不通，只能设置父元素的大小，让TableView自适应
     *
     * @param parentNode  父容器
     * @param tableView  tableView对象
     * @param columnList 表格字段集合
     * @param dataList   表格数据集合
     */
    public static void createDynamicTable(Pane parentNode, TableView tableView, List<TableColumn> columnList, List dataList) {
        //AssertUtils.assertTrue(Objects.isNull(tableView), "Component is Null!");
        // 设置表格可编辑
        tableView.setEditable(true);
        // 设置多选
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // 去掉空白多于列
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // 设置表头
        tableView.getColumns().addAll(columnList);
        // 更新表格数据
        ObservableList<Object> data = FXCollections.observableArrayList(dataList);
        tableView.setItems(data);
        // 拖动行
        tableView.setRowFactory(value -> {
            TableRow<ObservableList<String>> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2 && !row.isEmpty()) {
                    ObservableList items = tableView.getItems();
                    int rowIndex = row.getIndex();
                    System.out.println("行数据:" + items.get(rowIndex));
                }
            });
            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(DataFormat.PLAIN_TEXT, index);
                    db.setContent(cc);
                    event.consume();
                }
            });
            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(DataFormat.PLAIN_TEXT)) {
                    if (row.getIndex() != ((Integer) db.getContent(DataFormat.PLAIN_TEXT)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });
            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(DataFormat.PLAIN_TEXT)) {
                    int draggedIndex = (Integer) db.getContent(DataFormat.PLAIN_TEXT);
                    ObservableList<String> draggedPerson = (ObservableList<String>)tableView.getItems().remove(draggedIndex);
                    int dropIndex;
                    if (row.isEmpty()) {
                        dropIndex = tableView.getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                    }
                    tableView.getItems().add(dropIndex, draggedPerson);
                    event.setDropCompleted(true);
                    tableView.getSelectionModel().select(dropIndex);
                    event.consume();
                }
            });
            return row;
        });
    }


    /**
     * 根据isCheckBox、columns创建表格表头(BaseEntity)
     *
     * @param isCheckBox 是否创建多选列
     * @param columns    bean字段对应中文集合
     * @return
     */
    public static List<TableColumn> createColumn(boolean isCheckBox, LinkedHashMap<String, String> columns, List<String> delList, List<String> updList) {
        List<TableColumn> columnList = new ArrayList<>();
        // 多选框
        if (isCheckBox) {
            TableColumn<BaseEntity, CheckBox> checkCol = new TableColumn("单选框");
            checkCol.setMinWidth(50);
            checkCol.setPrefWidth(50);
            checkCol.setResizable(false);
            checkCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BaseEntity, CheckBox>, ObservableValue<CheckBox>>() {
                @Override
                public ObservableValue<CheckBox> call(TableColumn.CellDataFeatures<BaseEntity, CheckBox> param) {
                    CheckBox checkBox = new CheckBox();
                    // 设置checkBox居中，貌似没用
                    checkBox.setAlignment(Pos.CENTER);
                    checkBox.setOnAction(event -> {
                        boolean selected = checkBox.isSelected();
                        String gid = param.getValue().getGid();
                        if (selected) {
                            delList.add(gid);
                            updList.removeAll(updList);
                            updList.add(gid);
                            System.out.println("选中: " + gid);
                        } else {
                            delList.remove(gid);
                            updList.remove(gid);
                            System.out.println("取消选中: " + gid);
                        }
                    });
                    return new ReadOnlyObjectWrapper<CheckBox>(checkBox);
                }
            });
            columnList.add(checkCol);
        }

        // 序号列
        TableColumn seqCol = new TableColumn("序号");
        seqCol.setMinWidth(50);
        seqCol.setPrefWidth(50);
        seqCol.setResizable(false);
       // seqCol.setCellFactory(new IDCell<>());
        columnList.add(seqCol);

        Map<String, Double> defaultWidthMap = getDefaultWidthMap();

        // 创建其他表头
        columns.forEach((k, v) -> {
            TableColumn column = new TableColumn(v);
            if ("gid".equals(k)) {
                column.setVisible(false);
            }
            if (getDateColSet().contains(k)) {
                formatDateTimeCol(column);
            }
            if (Objects.isNull(defaultWidthMap.get(k))) {
                // 没有默认长度，设置100宽度
                column.setMinWidth(100);
                column.setPrefWidth(100);
            } else {
                column.setMinWidth(defaultWidthMap.get(k));
                column.setPrefWidth(defaultWidthMap.get(k));
            }
            column.setResizable(false);
            column.setCellValueFactory(new PropertyValueFactory<BaseEntity, String>(k));
            columnList.add(column);
        });
        return columnList;
    }




    /**
     * 格式化时间字段(Java8)
     *
     * @param column
     */
    public static void formatDateTimeCol(TableColumn column) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        column.setCellFactory(data -> {
            TableCell<Object, LocalDateTime> cell = new TableCell<Object, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        if (item != null)
                            this.setText(format.format(item));
                    }
                }
            };
            return cell;
        });
    }


    /**
     * 格式化时间字段(Java7)
     *
     * @param column
     */
    public static void formatDateCol(TableColumn column) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        column.setCellFactory(data -> {
            TableCell<Object, Date> cell = new TableCell<Object, Date>() {
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        if (item != null)
                            this.setText(format.format(item));
                    }
                }
            };
            return cell;
        });
    }


    /**
     * 获取时间字段集合
     *
     * @return
     */
    public static Set<String> getDateColSet() {
        Set<String> colSet = new HashSet<>();
        colSet.add("createTime");
        colSet.add("editTime");
        return colSet;
    }


    /**
     * 刷新tableView
     *
     * @param tableView
     * @param dataList
     */
    public static void refresh(TableView tableView, List dataList) {
        ObservableList items = tableView.getItems();
        items.removeAll(items);
        items.addAll(dataList);
        tableView.refresh();
    }

    private static Map<String, Double> columnWidthMap = new HashMap<>();
    static {
        columnWidthMap.put("gid", 350.00);
        columnWidthMap.put("account", 100.00);
        columnWidthMap.put("code", 100.00);
        columnWidthMap.put("name", 100.00);
        columnWidthMap.put("description", 100.00);
        columnWidthMap.put("remark", 100.00);
        columnWidthMap.put("createBy", 100.00);
        columnWidthMap.put("createTime", 200.00);
        columnWidthMap.put("editBy", 100.00);
        columnWidthMap.put("editTime", 200.00);
        columnWidthMap.put("username", 200.00);
        columnWidthMap.put("password", 200.00);
    }

    /**
     * 获取默认字段对应长度集合
     *
     * @return
     */
    public static Map<String, Double> getDefaultWidthMap() {
        return columnWidthMap;
    }

    //====================================================对tableView代码提取===========================================


    /**
     * 初始化下拉框
     *
     * @param choiceBox 下拉框对象
     * @param dataList  下拉框数据
     */
    public static void createChoiceBox(ChoiceBox choiceBox, List dataList) {
        dataList.add("");
       // AssertUtils.assertTrue(Objects.isNull(choiceBox), "Component is Null!");
        // 下拉框数据
        ObservableList observableList = FXCollections.observableArrayList(dataList);
        // 装填数据
        choiceBox.setItems(observableList);
        // 选择监听事件
        choiceBox.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            //System.out.println("选择了: " + dataList.get(new_val.intValue()));
        });
    }

    /**
     * 初始化下拉框
     *
     * @param choiceBox 下拉框对象
     * @param dataList  下拉框数据
     */
    public static void createChomboBox(ComboBox choiceBox, List dataList) {
        dataList.add("");
       // AssertUtils.assertTrue(Objects.isNull(choiceBox), "Component is Null!");
        // 下拉框数据
        ObservableList observableList = FXCollections.observableArrayList(dataList);
        // 装填数据
        choiceBox.setItems(observableList);
        // 选择监听事件
        choiceBox.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            //System.out.println("选择了: " + dataList.get(new_val.intValue()));
        });
    }


    /**
     * 根据dataList、config初始化listView组件
     *
     * @param listView listView组件
     * @param dataList 数据
     * @param config   组件配置
     */
    public static void createListView(ListView<String> listView, List<String> dataList, Map<String, Object> config) {
        //AssertUtils.assertTrue(Objects.isNull(listView), "Component is Null!");
        // 设置组件宽高
        Object width = config.get("width");
        Object height = config.get("height");
        if (Objects.nonNull(width)) {
            double widthValue = Double.parseDouble(width.toString());
            listView.setPrefWidth(widthValue);
        }
        if (Objects.nonNull(height)) {
            double heightValue = Double.parseDouble(height.toString());
            listView.setPrefHeight(heightValue);
        }
        // 装填数据
        ObservableList<String> items = FXCollections.observableArrayList(dataList);
        listView.setItems(items);
        // 绑定选中事件
        listView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            System.out.println(new_val);
        });
    }


    //====================================================对fileChooser代码提取=========================================


    /**
     * 创建文件选择器
     *
     * @param stage
     * @param fileChooser
     * @return
     */
    public static File createFileChoose(Stage stage, FileChooser fileChooser) {
       // AssertUtils.assertTrue(Objects.isNull(fileChooser), "Component is Null!");
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);
        return file;
    }


    /**
     * 创建多文件选择器
     *
     * @param stage
     * @param fileChooser
     * @return
     */
    public static List<File> createMultipleFileChooser(Stage stage, FileChooser fileChooser) {
       // AssertUtils.assertTrue(Objects.isNull(fileChooser), "Component is Null!");
        fileChooser.setTitle("Open Resource File");
        List<File> fileList = fileChooser.showOpenMultipleDialog(stage);
        return fileList;
    }


    /**
     * 创建文件夹选择器
     *
     * @param stage
     * @param directoryChooser
     * @return
     */
    public static File createDirectoryChooser(Stage stage, DirectoryChooser directoryChooser) {
       // AssertUtils.assertTrue(Objects.isNull(directoryChooser), "Component is Null!");
        directoryChooser.setTitle("Open Resource File");
        File file = directoryChooser.showDialog(stage);
        return file;
    }


    //====================================================对DatePicker代码提取==========================================


    /**
     * 初始化日期选择器
     *
     * @param datePicker
     */
    public static void createDatePicker(DatePicker datePicker) {
       // AssertUtils.assertTrue(Objects.isNull(datePicker), "Component is Null!");
        String pattern = "yyyy-MM-dd";
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

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
        };
        datePicker.setConverter(converter);
        datePicker.setPromptText(pattern.toLowerCase());
    }


    /**
     * 监听日期选择器(开始时间选择后结束时间不能超过开始时间)
     *
     * @param beginTime
     * @param endTime
     */
    public static void enableRange(DatePicker beginTime, DatePicker endTime) {
        if(Objects.isNull(beginTime.getValue())) {
            beginTime.setValue(LocalDate.now().minusDays(1));
        }
        if(Objects.isNull(endTime.getValue())) {
            endTime.setValue(LocalDate.now());
        }
        Callback<DatePicker, DateCell> endTimeCallback = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if(Objects.nonNull(beginTime)) {
                            if(item.isBefore(beginTime.getValue().plusDays(1))) {
                                setDisable(true);
                            }
                        }
                    }
                };
            }
        };

        Callback<DatePicker, DateCell> beginTimeCallback = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if(Objects.nonNull(endTime)) {
                            if(item.isAfter(endTime.getValue().minusDays(1))) {
                                setDisable(true);
                            }
                        }
                    }
                };
            }
        };

        beginTime.setDayCellFactory(beginTimeCallback);
        endTime.setDayCellFactory(endTimeCallback);

        // 时间选择器监听器，监听用户选择日期
        beginTime.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                LocalDate endTimeValue = endTime.getValue();
                if(Objects.nonNull(endTimeValue)) {
                    if(newValue.isAfter(endTimeValue) || newValue.isEqual(endTimeValue)) {
                        CommonUtil._alertInformation("选择时间不能大于等于结束时间!");
                    }
                }
            }
        });
        endTime.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                LocalDate beginTimeValue = beginTime.getValue();
                if(Objects.nonNull(beginTimeValue)) {
                    if(newValue.isBefore(beginTimeValue) || newValue.isEqual(beginTimeValue)) {
                        CommonUtil._alertInformation("选择时间不能小于等于开始时间!");
                    }
                }
            }
        });
    }


    //======================================================对进度条代码提取============================================

    private static ReentrantLock lock = new ReentrantLock();

    /**
     * 初始化进度条
     *
     * @param progressBar
     */
    public static void createProgressBar(ProgressBar progressBar) {
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(null);
    }


    /**
     * 更新进度条
     *
     * @param progressBar
     */
    public static void updateVal(ProgressBar progressBar) {
        lock.lock();
        try {
            progressBar.setProgress(1);
        } finally {
            lock.unlock();
        }
    }


    /**
     * 清空进度条
     *
     * @param progressBar
     */
    public static void clear(ProgressBar progressBar) {
        progressBar.setProgress(0);
    }


    /**
     * 方法描述：初始化视频、音乐播放器
     * @param filePath          播放文件
     * @param config       播放器(没有这个参数)
     * @param videoSlider       视频、音乐进度条
     * @param volumeSlider      音量进度条
     * @param playBtn           播放、暂停按钮
     * @param currentTimeLabel  当前时间
     * @param totalTimeLabel    视频、音乐总时间
     */
    public static void initMediaPlayer(String filePath, Slider videoSlider, Slider volumeSlider, Button playBtn, Label currentTimeLabel, Label totalTimeLabel, HashMap<String, String> config) {
        // 初始化播放器
        String url = new File("E:\\CloudMusic\\陈光荣 - Lost Good Things.mp3").getAbsoluteFile().toURI().toString();
        Media media = new Media(url);
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        // 音量监听器
        mediaPlayer.volumeProperty().bind(volumeSlider.valueProperty().divide(100));

        // 进度条改变，音乐播放进度也改变
        videoSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(videoSlider.isValueChanging()) {
                    mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(videoSlider.getValue() / 100));
                }
            }
        });

        // 播放按钮绑定事件
        playBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // 设置音乐总时间
                double seconds = mediaPlayer.getTotalDuration().toSeconds();
                Long value = null;
                try {
                    value = new Double(seconds).longValue();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                totalTimeLabel.setText(formatDateTime(value));

                String text = playBtn.getText();
                if("暂停".equals(text)) {
                    playBtn.setText("播放");
                    mediaPlayer.pause();
                }
                if("播放".equals(text)) {
                    // 开始播放音乐
                    playBtn.setText("暂停");
                    mediaPlayer.play();
                    // 设置播放进度条
                    videoSlider.setMin(0.0);
                    videoSlider.setMax(mediaPlayer.getTotalDuration().toSeconds());
                    mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                        @Override
                        public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                            videoSlider.setValue(newValue.toSeconds());
                            // 更新当前时间
                            double currentSecond = newValue.toSeconds();
                            long currentValue = new Double(currentSecond).longValue();
                            currentTimeLabel.setText(formatDateTime(currentValue));
                        }
                    });

                    // 进度条改变，播放进度也改变
                    videoSlider.valueProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            if(videoSlider.isValueChanging()) {
                                mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(videoSlider.getValue() / 100));
                            }
                        }
                    });
                }
            }
        });

    }


    private static String formatDateTime(long mss) {
        String time = null;
        long minutes = (mss % ( 60 * 60)) /60;
        long seconds = mss % 60;
        String minutesValue = null;
        if(String.valueOf(minutes).length() > 1) {
            minutesValue = String.valueOf(minutes);
        } else {
            minutesValue = String.valueOf("0" + minutes);
        }
        String secondsValue= null;
        if(String.valueOf(seconds).length() > 1) {
            secondsValue = String.valueOf(seconds);
        } else {
            secondsValue = String.valueOf("0" + seconds);
        }
        time = minutesValue + ":" + secondsValue;
        return time;
    }

}
