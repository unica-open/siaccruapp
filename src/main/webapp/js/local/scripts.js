/*
*SPDX-FileCopyrightText: Copyright 2020 | CSI Piemonte
*SPDX-License-Identifier: EUPL-1.2
*/
(function(){
    //get the background-color for each linkBox and apply it as background color for the cooresponding screen
	var settingMargin = 120;
	var intElemOffsetHeight = document.getElementById('contentDashboard').offsetHeight+settingMargin;
	
    $('.linkBox').each(function(){
        var $this= $(this),
            modalId = $this.data('modal-id'),
            bgcolor = $this.css('background-color'),
            textColor = $this.css('color');

            //if the tile rotates, we'll use the colors of the front face
            if($this.hasClass('rotate3d')) {
              frontface = $this.find('.front');
              bgcolor = frontface.css('background-color');
              textColor = frontface.css('color');
            }

            //if the tile has an image and a caption, we'll use the caption styles
            if($this.hasClass('fig-tile')) {
              caption = $this.find('figcaption');
              bgcolor = caption.css('background-color');
              textColor = caption.css('color');
            }

        $this.on('click',function(){
		if($this.data('page-type') === "s-page" ){
				$(modalId +' .page-contentSlide').css({'background-color': bgcolor, 'color': textColor})
				/* .find('.close-button').css({'background-color': textColor, 'color': bgcolor}); */
			  
			  }
			  else{
				 $(modalId +' .page-contentSlide').css({'background-color': bgcolor, 'color': textColor})
			  };
        });
    });

	  function showDashBoard(){
      for(var i = 1; i <= 3; i++) {
        $('.col'+i).each(function(){
            $(this).addClass('fadeInForward-'+i).removeClass('fadeOutback');
        });
      }
    } 

    function fadeDashBoard(){
      for(var i = 1; i <= 3; i++) {
        $('.col'+i).addClass('fadeOutback').removeClass('fadeInForward-'+i);
      }
    }
  
    
  //listen for when a linkBox is clicked
  //retrieve the type of page it opens from its data attribute
  //based on the type of page, add corresponding class to page and fade the dashboard
  
  $('.linkBox').each(function(){
    var $this= $(this),
        pageType = $this.data('page-type'),
        modalId = $this.data('modal-id');
        
    $this.on('click',function(){
	
      if(pageType === "s-page"){
          fadeDashBoard();

			$(modalId).each(function(){
				$(this).css({'height': intElemOffsetHeight})			

			});
		   
			$(modalId).animate({
				opacity: 1,
				left: "0",
				width:"easeOutBounce"
			  }, 1000, function() {
			  
				/* $(document).scrollTop(0); */
				 $("html, body").animate({ scrollTop: 0 }, "slow");
				return false;
  
			});
			
			$(modalId).addClass('open');
			  		  
        }
        else{
          $(modalId).addClass('openpage');

        }
    });
  });

  //when a close button is clicked:
  //close the page
  //wait till the page is closed and fade dashboard back in
  
  $('.att-close-button').click(function(){
  
	$(this).parent().parent().removeClass('openpage');
	
    /*   $(this).parent().addClass('slidePageLeft')
          .one('webkitAnimationEnd oanimationend msAnimationEnd animationend', function(e) {
                $(this).removeClass('slidePageLeft').removeClass('openpage');
              }); */

     /*  showDashBoard(); */
  });
  
  $('.s-close-button').click(function(){
 
	   $(this).parent().parent().removeClass('open').animate({
					left: -2000
				},500);
					
					
      /* showDashBoard(); */
  });
  
  //patch di Goy per allineare i box tutti con la stessa altezza 
  if ($('.box').length){
      var newheight = 0;
      $(".box").each(function() {
          if ($(this).height() > newheight) {
              newheight = $(this).height();
          };
      });
      $(".box").css("height",newheight);
  }
  
  $('[data-toggle="tooltip"]').tooltip({
      placement : 'top'
  });

  
  $(document).on('keydown', function(event) {
	if (event.key == "Escape") {
		$('.open .s-close-button').trigger('click');
	}
  });
		

})();