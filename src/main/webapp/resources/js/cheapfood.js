$(document).ready(function(){

    "use strict";

    var map;
    var moscowCenter = {lat: 55.764283, lng: 37.606614};
    var ZOOM_LEVEL = 10;

    var TYPE_IMAGE_WIDTH = 128;
    var EFFECTS_TIME = 250;


    init();

    function init() {
        createMap();
        createContextMenuForMap();
        centerMapToLocation();
        createMarkersForLocations();
        createMarkerEditFormOnMap();
    }


    function createMap() {
        map = new GMaps({
            div: '#map',
            lat: moscowCenter.lat,
            lng: moscowCenter.lng,
            zoom: ZOOM_LEVEL,
            streetViewControl: true,
            panControl: true,
            mapTypeControl: false,
            markerClusterer: function(map) {
                return new MarkerClusterer(map);
            }
        });
    }

    function createContextMenuForMap() {
        map.setContextMenu({
            control: 'map',
            options: [
                {
                    title: 'Создать точку',
                    name: 'add_location',
                    action: function(e) {
                        this.addMarker({
                            lat: e.latLng.lat(),
                            lng: e.latLng.lng(),
                            title: 'New marker'
                        });
                    }
                }
            ]
        });
    }

    function centerMapToLocation() {
        GMaps.geolocate({
            success: function(position) {
                map.setCenter(position.coords.latitude, position.coords.longitude);
            },
            error: function(error) {
                map.setCenter( moscowCenter.lat, moscowCenter.lng );
            },
            not_supported: function() {
                map.setCenter( moscowCenter.lat, moscowCenter.lng );
            },
            always: function() {}
        });

    }

    function createMarkersForLocations() {

        var infoBox = createInfoBoxForMarkers();

        $.get("api/locations", function(data) {

            $.each(data, function(n, location) {
                createMarkerWithInfoBoxForLocation(location, infoBox);
            });
        });
    }

    function createMarkerEditFormOnMap() {
        var div = $('<div/>').attr('id','editMarkerFormDiv').attr('hidden', 'true')
            .addClass('span6 transparent infoWindow').append(getMarkerEditContent());
        var mapControls = map.map.controls[google.maps.ControlPosition.TOP_RIGHT];

        map.map.controls[google.maps.ControlPosition.TOP_RIGHT].push(div.get(0));
    }

    function createInfoBoxForMarkers() {
        var boxText = document.createElement("div");
        boxText.id = 'infoBox';
        boxText.innerHTML = getMarkerContentFromLocation(location);

        var infoOptions = {
            content: boxText,
            boxClass: "span5 transparent infoWindow",
            disableAutoPan: false,
            isHidden: true,
            pane: "floatPane",
            infoBoxClearance: new google.maps.Size(50, 50),
            maxWidth: 500,
            pixelOffset: new google.maps.Size(0, -150),
            enableEventPropagation: false
        };
        var infoBox = new InfoBox(infoOptions);

        return infoBox;
    }

    function createMarkerWithInfoBoxForLocation(location, infoBox) {

        var pos = getLatLngFromText(location.geoLocation);

        var marker = map.addMarker({
            lat: pos.lat(),
            lng: pos.lng(),
            title: location.title,
            click: function() {

                if( !isEditMarkerFormActive() ) {
                    infoBox.open(map.map, marker);
                    infoBox.show();

                    google.maps.event.addListener(infoBox, 'domready', function() {
                        setInfoBoxContentFromLocation(location);
                        initInfoBoxButtonsBehavior(location, infoBox);
                    });
                }
            }
        });
    }

    function setInfoBoxContentFromLocation(location) {

        $('#info_title').text(location.title);
        $('#info_type').text(location.type);
        $('#info_description').text(location.description);
        $('#info_addressDescription').text(location.addressDescription);
        $('#info_address').text(location.address);
        $('#info_actualDate').text(location.actualDate);

    }

    function initInfoBoxButtonsBehavior(location, infoBox) {

        initScrollInfoBoxBehavior();
        initToggleEditAndViewBehavior(location, infoBox);
    }

    function initScrollInfoBoxBehavior() {
        //https://code.google.com/p/google-maps-utility-library-v3/issues/detail?id=19#c8
        //this is for scrolling into the InfoBox
        $('#infoBox').on('mouseenter', function() {
            map.setOptions({draggable:false, scrollwheel:false});
        });
        $('#infoBox').on('mouseleave', function() {
            map.setOptions({draggable:true, scrollwheel:true});
        });
        //end of scrolling
    }

    function isEditMarkerFormActive() {
        if( $('#editMarkerFormDiv').is(':visible') ) {
              return true;
        }
        else {
            return false;
        }
    }

    function initToggleEditAndViewBehavior(location, infoBox) {

        $('#editMarkerButton').click(function(){

            map.map.setCenter( infoBox.getPosition() );
            infoBox.hide();

            $('#cancelEdit').click(function() {
                $('#editMarkerFormDiv').fadeOut(EFFECTS_TIME);
            });

            $('#editMarkerFormDiv').fadeIn(EFFECTS_TIME, function() {
                initEditFormFocus();
                initDatePicker();
                initSwitch();
            });
        });
    }

    function initEditFormFocus() {
        $('#title').focus();
    }

    function initDatePicker() {
        $('.datepicker').datepicker({
            language: 'ru'
        });
        $('.datepicker').datepicker().on('changeDate', function(e){
            $('.datepicker').datepicker('hide');
        });
    }

    function initSwitch() {

        $('#createAddressSwitch').click(function(){
            if($(this).parent().find('input').is(':checked')) {
                $('#realAddress').show(EFFECTS_TIME);
            }
            else {
                $('#realAddress').hide(EFFECTS_TIME);
            }
        });
    }

    function getMarkerContentFromLocation(location) {

        var res = getMarkerContentElementFromLocation(location);

        return res.html();
    }

    function getMarkerContentElementFromLocation(location) {

        var exRes = $('<div/>');

        var res = $('<ul/>').attr('id', 'infoContent').addClass('media-list')
            .append(
                $('<li/>').addClass('media')
                    .append(
                        $('<a/>').addClass('pull-left img-with-text').attr('href', '#')
                            .append(
                                $('<img/>').addClass('media-object').attr('src', 'resources/img/basic.png')
                                    .attr('width', TYPE_IMAGE_WIDTH)
                            )
                            .append(
                                $('<p/>').attr('align', 'center').append(
                                    $('<span/>').attr('id', 'info_type').addClass('label label-info').text(location.type)
                                )
                            )
                    )
                    .append(
                        $('<div/>').addClass('media-body')
                            .append(
                                $('<h4/>').attr('id', 'info_title').addClass('media-heading').text(location.title)
                            )
                            .append(
                                $('<p/>').attr('id', 'info_description').text(location.description)
                            )
                            .append(
                                $('<div/>').addClass('media')
                                    .append(
                                        $('<div/>').addClass('media-body')
                                            .append(
                                                $('<span/>').addClass('label label-info').text('Адрес')
                                            )
                                            .append(
                                                $('<span/>').attr('id','info_addressDescription')
                                                    .addClass('spacer5').text(location.addressDescription)
                                            )
                                    )
                                    .append(
                                        $('<div/>').addClass('media-body')
                                            .append(
                                                $('<span/>').addClass('label').text('Реальный адрес')
                                            )
                                            .append(
                                                $('<span/>').attr('id', 'info_address').addClass('spacer5')
                                                    .text(location.address)
                                            )
                                    )
                                    .append(
                                        $('<div/>').addClass('media-body')
                                            .append(
                                                $('<span/>').addClass('label').text('Дата проверки')
                                            )
                                            .append(
                                                $('<span/>').attr('id', 'info_actualDate').addClass('spacer5')
                                                    .text(location.actualDate)
                                            )
                                    )
                            )
                    )
            )
            .append(
                $('<div/>').addClass('media-body')
                    .append(
                        $('<div/>').addClass('media')
                            .append(
                                $('<div/>').addClass('btn-toolbar')
                                    /*.append(
                                        $('<div/>').addClass('btn-group')
                                            .append(
                                                $('<button/>').addClass('btn btn-small').text('Подробнее')
                                            )
                                    ) */
                                    .append(
                                        $('<div/>').addClass('btn-group')
                                            .append(
                                                $('<button/>').addClass('btn btn-small').text('Маршрут')
                                            )
                                            .append(
                                                $('<button/>').addClass('btn btn-small dropdown-toggle')
                                                    .attr('data-toggle','dropdown')
                                                    .append(
                                                        $('<span/>').addClass('caret')
                                                    )
                                            )
                                            .append(
                                                $('<ul/>').addClass('dropdown-menu')
                                                    .append(
                                                        $('<li/>').append(
                                                            $('<a/>').attr('href', '#').text('Сюда')
                                                        )
                                                    )
                                                    .append(
                                                        $('<li/>').append(
                                                            $('<a/>').attr('href', '#').text('Отсюда')
                                                        )
                                                    )
                                            )
                                    )
                                    .append(
                                        $('<div/>').addClass('btn-group pull-right')
                                            .append(
                                                $('<button/>').attr('id', 'editMarkerButton').
                                                    addClass('btn btn-primary btn-small').text('Редактировать')
                                            )
                                    )
                            )
                    )
            )
            .append(
                $('<hr/>')
            )
            .append(
                $('<div/>').addClass('media-body')
                    .append(
                        $('<div/>').addClass('media')
                            .append(
                                $('<div/>').addClass('btn-toolbar')
                                    .append(
                                        $('<div/>').addClass('btn-group pull-left')
                                            .append(
                                                $('<button/>').addClass('btn btn-success btn-small').text('Подтверждаю точку')
                                            )
                                    )
                                    .append(
                                        $('<div/>').addClass('btn-group pull-right')
                                            .append(
                                                $('<button/>').addClass('btn btn-warning btn-small').text('Точки здесь больше нет')
                                            )
                                    )
                            )
                    )
            );

        exRes.append(res);
        return exRes;
    }

    function getMarkerEditContent() {

        var res = $('<form/>').attr('id', 'editMarkerForm').addClass('form-horizontal scrollable')
            .append(
                $('<legend/>').text('Создание точки')
            )
            .append(
                $('<div/>').addClass('control-group')
                    .append(
                        $('<label/>').addClass('control-label').attr('for','title').text('Название')
                    )
                    .append(
                        $('<div/>').addClass('controls')
                            .append(
                                $('<input/>').addClass('input-block-level span4').attr('id', 'title')
                                    .attr('placeholder', 'Чебуречная Ашота')
                            )
                    )
            )
            .append(
                $('<div/>').addClass('control-group')
                    .append(
                        $('<label/>').addClass('control-label').attr('for','description').text('Описание')
                    )
                    .append(
                        $('<div/>').addClass('controls')
                            .append(
                                $('<input/>').addClass('input-block-level span4').attr('id', 'description')
                                    .attr('placeholder', 'Самые лучшие чебуреки и шаурма в городе!')
                            )
                    )
            )
            .append(
                $('<div/>').addClass('control-group')
                    .append(
                        $('<label/>').addClass('control-label').attr('for','type').text('Тип')
                    )
                    .append(
                        $('<div/>').addClass('controls')
                            .append(
                                $('<select/>').attr('id', 'type')
                                    .append(
                                        $('<option/>').text('Шаверменная')
                                    )
                                    .append(
                                        $('<option/>').text('Чебуречная')
                                    )
                                    .append(
                                        $('<option/>').text('Шашлычная')
                                    )
                                    .append(
                                        $('<option/>').text('Булочная')
                                    )
                                    .append(
                                        $('<option/>').text('Столовая')
                                    )
                                    .append(
                                        $('<option/>').text('Кафе')
                                    )
                                    .append(
                                        $('<option/>').text('Другое')
                                    )
                            )
                    )
            )
            .append(
                $('<div/>').addClass('control-group')
                    .append(
                        $('<div/>').addClass('controls')
                            .append(
                                $('<label/>').addClass('radio')
                                    .append(
                                        $('<input/>').attr('id', 'footype1').attr('name', 'optionsRadios')
                                            .attr('type', 'radio')
                                            .attr('value', 'true').attr('checked', 'true')
                                    )
                                    .append(
                                        $('<span/>').text('Это тошняк или палатка')
                                    )
                            )
                            .append(
                                $('<label/>').addClass('radio')
                                    .append(
                                        $('<input/>').attr('id', 'footype2').attr('name', 'optionsRadios')
                                            .attr('type', 'radio')
                                            .attr('value', 'false')
                                    )
                                    .append(
                                        $('<span/>').text('Это нормальное кафе')
                                    )
                            )
                    )
            )
            .append(
                $('<div/>').addClass('control-group')
                    .append(
                        $('<label/>').addClass('control-label').attr('for','addressDescription').text('Описание адреса')
                    )
                    .append(
                        $('<div/>').addClass('controls')
                            .append(
                                $('<input/>').addClass('input-block-level span4').attr('id', 'addressDescription')
                                    .attr('placeholder', 'Выходите с электрички и спускаетесь под мост')
                            )
                    )
            )
            .append(
                $('<div/>').addClass('control-group').attr('hidden', 'true')
                    .append(
                        $('<label/>').addClass('control-label').attr('for','latitude').text('Latitude')
                    )
                    .append(
                        $('<div/>').addClass('controls')
                            .append(
                                $('<input/>').addClass('input-block-level span4').attr('id', 'latitude')
                                    .attr('hidden', 'true')
                            )
                    )
            )
            .append(
                $('<div/>').addClass('control-group').attr('hidden', 'true')
                    .append(
                        $('<label/>').addClass('control-label').attr('for','longitude').text('Longitude')
                    )
                    .append(
                        $('<div/>').addClass('controls')
                            .append(
                                $('<input/>').addClass('input-block-level span4').attr('id', 'longitude')

                            )
                    )
                )
            .append(
                    $('<div/>').addClass('control-group')
                        .append(
                            $('<label/>').addClass('control-label').attr('for', 'actualDate').text('Дата проверки')
                        )
                        .append(
                            $('<div/>').addClass('controls')
                                .append(
                                    $('<div/>').addClass('input-append date datepicker')
                                        .attr('data-date', '13-07-2013').attr('data-date-format', 'dd-mm-yyyy')
                                        .append(
                                            $('<input/>').attr('id', 'actualDate').attr('type', 'text')
                                                .attr('value', '13-07-2013')
                                        )
                                        .append(
                                            $('<span/>').addClass('add-on')
                                                .append(
                                                    $('<i/>').addClass('icon-th')
                                                )
                                        )
                                )
                        )
            )
                .append(
                    $('<hr/>')
                )
                .append(
                    $('<div/>').addClass('control-group')
                        .append(
                            $('<label/>').addClass('control-label').attr('for', 'addressSwitch').text('Знаете реальный адрес?')
                        )
                        .append(
                            $('<div/>').addClass('controls')
                                .append(
                                    $('<div/>').addClass('switch').attr('id', 'addressSwitch')
                                        .append(
                                            $('<input/>').attr('type', 'checkbox').attr('id','createAddressSwitch')
                                        )
                                )
                        )
                )
                .append(
                    $('<div/>').attr('id', 'realAddress').attr('hidden', 'true')
                        .append(
                            $('<div/>').addClass('control-group')
                                .append(
                                    $('<label/>').addClass('control-label').attr('for','country').text('Страна')
                                )
                                .append(
                                    $('<div/>').addClass('controls')
                                        .append(
                                            $('<input/>').addClass('input-block-level span4').attr('id', 'country')

                                        )
                                )
                        )
                        .append(
                            $('<div/>').addClass('control-group')
                                .append(
                                    $('<label/>').addClass('control-label').attr('for','region').text('Регион')
                                )
                                .append(
                                    $('<div/>').addClass('controls')
                                        .append(
                                            $('<input/>').addClass('input-block-level span4').attr('id', 'region')

                                        )
                                )
                        )
                        .append(
                            $('<div/>').addClass('control-group')
                                .append(
                                    $('<label/>').addClass('control-label').attr('for','city').text('Город')
                                )
                                .append(
                                    $('<div/>').addClass('controls')
                                        .append(
                                            $('<input/>').addClass('input-block-level span4').attr('id', 'city')

                                        )
                                )
                        )
                        .append(
                            $('<div/>').addClass('control-group')
                                .append(
                                    $('<label/>').addClass('control-label').attr('for','address').text('Улица, дом итд')
                                )
                                .append(
                                    $('<div/>').addClass('controls')
                                        .append(
                                            $('<input/>').addClass('input-block-level span4').attr('id', 'address')

                                        )
                                )
                        )
                        .append(
                            $('<div/>').addClass('control-group')
                                .append(
                                    $('<label/>').addClass('control-label').attr('for','zipcode').text('Индекс')
                                )
                                .append(
                                    $('<div/>').addClass('controls')
                                        .append(
                                            $('<input/>').addClass('input-block-level span4').attr('id', 'zipcode')

                                        )
                                )
                        )
                )
            .append(
                $('<div/>').addClass('form-actions')
                    .append(
                        $('<button/>').attr('id','submitEdit').attr('type', 'button').addClass('btn btn-primary')
                            .text('Сохранить точку')
                    )
                    .append(
                        $('<button/>').attr('id','cancelEdit').attr('type', 'button').addClass('btn').text('Отмена')
                    )
            );

        return res;
    }

    ///  help functions
    function getLatLngFromText(geoLocation) {
        return new google.maps.LatLng( geoLocation.latitude, geoLocation.longitude );
    }
});

