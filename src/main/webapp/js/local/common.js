/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
!function() {
    'use strict';
    $(init);
    
    function init() {
        init_bwizard();
        init_datepicker();
        init_selectpicker();
        init_datatable();
        init_btnVisibile();
        init_riaccVisible();
        init_crTitle();
        init_conferma();
        init_chosen();
        init_tooltip();
    }
    
    function init_bwizard() {
        $("#wizard").bwizard();
    }
    function init_datepicker() {
        $('.datepicker').datepicker({language:'it'});
    }
    function init_selectpicker() {
        $(".selectpicker").selectpicker();
    }
    function init_datatable() {
        $('#risultatiricerca').dataTable({
            "sScrollY": 300,
            "sDom": "<'row datatableth'r>t<'row datatableth'<'span6'><'span6'>>",
            "sPaginationType": "bootstrap",
            "oLanguage": {
                "sProcessing": "Attendere prego...",
                "sZeroRecords": "Non sono presenti risultati di ricerca secondo i parametri inseriti",
                "sInfo": "_START_ - _END_ di _TOTAL_ risultati",
                "sInfoEmpty": "0 - 0 di 0 risultati",
                "sInfoFiltered": "(filtrato su _MAX_ risultati)",
                "sInfoPostFix": "",
                "sSearch": "Cerca",
                "sUrl": "",
                "oPaginate": {
                    "sFirst":    "Prima",
                    "sPrevious": "Prec.",
                    "sNext":     "Succ",
                    "sLast":     "Ultima"
                }
            }
        });
        $('table.oggettiTrovati').dataTable({
            "sDom": "<'row datatableth'<'span12'l>r>t<'row datatableth'<'span6'i><'span6'p>>",
            "sPaginationType": "bootstrap",
            "oLanguage": {
                "sProcessing": "Attendere prego...",
                "sZeroRecords": "Non sono presenti risultati di ricerca secondo i parametri inseriti",
                "sInfo": "_START_ - _END_ di _TOTAL_ risultati",
                "sInfoEmpty": "0 - 0 di 0 risultati",
                "sInfoFiltered": "(filtrato su _MAX_ risultati)",
                "sInfoPostFix": "",
                "sSearch": "Cerca",
                "sUrl": "",
                "oPaginate": {
                    "sFirst":    "Prima",
                    "sPrevious": "Prec.",
                    "sNext":     "Succ",
                    "sLast":     "Ultima"
                }
            }
        });
    }
    function init_btnVisibile() {
        $('.btnVisible').hide();
        var Statusbtn = true;
        var btnCheck=$('input[name=optionsRadios_riacc]');
        var btnIn=$('a[name=btnInvisible]');
        $(btnCheck).click(function(){
            var optRad=$(this).filter(':checked').val();
            //alert(radioVal);
            if(optRad=="si"){
                $('.btnVisible').show();
            }else{
                $('.btnVisible').hide();
            }
        });
        $(btnIn).click(function(){
            $('.btnVisible').hide();
        });
    }
    function init_riaccVisible() {
        $('.riaccVisible').hide();
        var riacc=$('input[name=optionsRadios_riacc]');
        $(riacc).click(function(){
            var optRad=$(this).filter(':checked').val();
           //alert(radioVal);
           if(optRad=="si"){
               $('.riaccVisible').show();
           }else{
               $('.riaccVisible').hide();
           }
       });
    }
    function init_crTitle() {
        $(document).on('click.cr_title', '[data-toggle=collapse]', function (e) {
            var $this = $(this);
            var href;
            var target = $this.attr('data-target')
                || e.preventDefault()
                //strip for ie7
                || (href = $this.attr('href')) && href.replace(/.*(?=#[^\s]+$)/, '');
            var option = $(target).data('collapse') ? 'toggle' : $this.data();
            if($this.hasClass('cr_title')){
                $this[$(target).hasClass('in') ? 'addClass' : 'removeClass']('arrow')
                $this[$(target).hasClass('in') ? '' : 'removeClass']('first_cr')
            }
        });
    }
    function init_conferma() {
        $(document).on("click", ".conferma", function(e) {
            var link=$(this).attr('rel');
            bootbox.dialog({
                className:'dialogAlert',
                title: "Attenzione!",
                message: "I dati non salvati andranno persi: sei sicuro di voler proseguire?",
                buttons: {
                    success: {
                        label: "Conferma",
                        className: "btn-primary",
                        callback: function() {
                            window.location.href=link;
                        }
                    },
                    main: {
                        label: "Chiudi",
                        className: "btn-primary",
                        callback: function() {
                        }
                    }
                }
            });
        });
    }
    function init_chosen() {
        var config = {
            '.chosen-select'           : {},
            '.chosen-select-deselect'  : {allow_single_deselect:true},
            '.chosen-select-no-single' : {disable_search_threshold:10},
            '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
            '.chosen-select-width'     : {width:"85%"}
        };
        for (var selector in config) {
            $(selector).chosen(config[selector]);
        }
    }
    function init_tooltip() {
        $('[data-toggle="tooltip"]').tooltip({
            placement : 'top'
        });
    }
}();
