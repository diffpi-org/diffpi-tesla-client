var NB = (function($){
	var that = {};
	
	that.defOption = {
			bootbox : {
				o : {
					locale : "zh_CN",
					className : "modal_align"
				},
				n : {
			        dialog: "<div class='bootbox modal' tabindex='-1' role='dialog'><div class='modal-dialog modal-lg'><div class='modal-content'><div class='modal-body'><div class='bootbox-body'></div></div></div></div></div>",
			        header: "<div class='modal-header '><h4 class='modal-title'></h4></div>",
			        footer: "<div class='modal-footer'></div>",
			        closeButton: "<button type='button' class='bootbox-close-button close btn-icon' data-dismiss='modal' aria-hidden='true'>&times;</button>",
			        form: "<form class='bootbox-form'></form>",
			        inputs: {
			            text: "<input class='bootbox-input bootbox-input-text form-control' autocomplete=off type=text />",
			            textarea: "<textarea class='bootbox-input bootbox-input-textarea form-control'></textarea>",
			            email: "<input class='bootbox-input bootbox-input-email form-control' autocomplete='off' type='email' />",
			            select: "<select class='bootbox-input bootbox-input-select form-control'></select>",
			            checkbox: "<div class='checkbox'><label><input class='bootbox-input bootbox-input-checkbox' type='checkbox' /></label></div>",
			            date: "<input class='bootbox-input bootbox-input-date form-control' autocomplete=off type='date' />",
			            time: "<input class='bootbox-input bootbox-input-time form-control' autocomplete=off type='time' />",
			            number: "<input class='bootbox-input bootbox-input-number form-control' autocomplete=off type='number' />",
			            password: "<input class='bootbox-input bootbox-input-password form-control' autocomplete='off' type='password' />"
			        }
			    }
			},
			
			sweet : {
		        title: "",
		        text: "",
		        type: "",
		        allowOutsideClick: !1,
		        showCancelButton: !1,
		        closeOnConfirm: !0,
		        closeOnCancel: !0,
		        confirmButtonText: "确定",
		        confirmButtonColor: "#66BB6A",
		        cancelButtonText: "取消",
		        imageUrl: null,
		        imageSize: null,
		        timer: null
			},
	};
	
	that.init = function(op){
		
		var option = $.extend({},that.defOption,op);
		
		bootbox.setDefaultOp(option.bootbox.o , option.bootbox.n);
		swal.setDefaults(option.sweet);
		
		return this;
	};
	
	/***
		{ 
		    size: 'small',
		    message: "Your message here…", 
		    callback: function(){  your callback code }
		}
		
		("Your message here…", function(){  your callback code  })
		
	*/
	
	that.alert = function(){
		var args = arguments;
		
		bootbox.alert.apply(this,args);
	}; 
	
	/***
	 * { 
		    size: 'small',
		    message: "Your message here…", 
		    callback: function(result){  your callback code }
		})
		
		confirm("Your message here…", function(result){  your callback code  })
	 */
	that.confirm = function(){
		var args = arguments;
		
		bootbox.confirm.apply(this,args);
	};
	
	/***
	 * ({ 
		    size: 'small',
		    message: "Your message here…", 
		    callback: function(result){ your callback code }
		})
		
		prompt("Your message here…", function(result){  your callback code  })
	 */
	that.prompt = function(){
		var args = arguments;
		
		bootbox.prompt.apply(this,args);
	};
	
	/***
	 * 
	 * {
		  // dialog的内容
		  message: "I am a custom dialog",
		  // dialog的标题
		  title: "Custom title",
		  // 退出dialog时的回调函数，包括用户使用ESC键及点击关闭
		  onEscape: function() {},
		  // 是否显示此dialog，默认true
		  show: true,
		  // 是否显示body的遮罩，默认true
		  backdrop: true,
		  // 是否显示关闭按钮，默认true
		  closeButton: true,
		  // 是否动画弹出dialog，IE10以下版本不支持
		  animate: true,
		  // dialog的类名
		  className: "my-modal",
		  // dialog底端按钮配置
		  buttons: {
		    // 其中一个按钮配置
		    success: {   
		      // 按钮显示的名称
		      label: "Success!",
		      // 按钮的类名
		      className: "btn-success",
		      // 点击按钮时的回调函数
		      callback: function() {}
		    },
		    // 另一个按钮配置
		    "Danger!": {
		      className: "btn-danger",
		      callback: function() {}
		    }
		  }
		}
	 */
	that.dialog = function(){
		var args = arguments;
		
		if(typeof args[0] === "object"){
			var opt = args[0];
			
			if(opt.url){
				$.ajax({
					url : opt.url,
					data : opt.data,
					type : opt.type || "post",
					async : false,
					success : function(content){
						args[0].message = content || "";
					}
				});
			}
		}
		
		bootbox.dialog.apply(this,args);
	};
	
	/***
	 * {
            title: 'Info notice',
            text: 'Check me out! I\'m a notice.',
            addclass: 'alert-styled-left',
            type: 'info'
        }
	 */
	that.notify = function(...arg){
		new PNotify(...arg);
	};
	
	/***
	 * {
	        title: "",
	        text: "",
	        type: "warning", "error", "success" and "info". You can also set it as "input" to
	        allowOutsideClick: !1,
	        showCancelButton: !1,
	        closeOnConfirm: !0,
	        closeOnCancel: !0,
	        confirmButtonText: "OK",
	        confirmButtonColor: "#AEDEF4",
	        cancelButtonText: "Cancel",
	        imageUrl: null,
	        imageSize: null,
	        timer: null
	    }
	    
	   ("Good job!", "You clicked the button!", "success")
	 */
	that.sweetAlert = function(...arg){
		swal(...arg);
	};
	
	that.sAlert = function(...arg){
		that.sweetAlert(...arg);
	};
	
	$.fn.extend({
		_jsonToForm:function(json){
			var form = $(this);
			if($.isPlainObject(json)){
				$.each(json,function(i,n){
					var field = form.find('input[name='+i+']');
					switch (field.attr('type')) {
					case 'hidden':
						field.val(n);
						break;
					case 'text':
						field.val(n);
						break;
					case 'radio':
						field.each(function(){
							var value = $(this).val();
							if(value == 'true'){value = true;}else if(value == 'false'){value = false;}
							if(value == n){$(this).get(0).checked = true;}
						});
						break;
					case 'checkbox':
						field.each(function(){
							var value = $(this).val();
							if(value == 'true'){value = true;}else if(value == 'false'){value = false;}
							if(value == n){$(this).get(0).checked = true;}
						});
						break;
					default:
						field = form.find('select[name='+i+']');
						if(!$.isEmptyObject(field.get(0))){
							field.children('option').each(function(){
								if($(this).val() == n){this.selected = true;}
							});
						}
						field = form.find('textarea[name='+i+']');
						if(!$.isEmptyObject(field.get(0))){
							field.val(n);
						}
						break;
					}
				});
			}
		},
		_formToJson:function(namePrefix){
			var o = {};
			var a = this.serializeArray();
			var obj;
			if(namePrefix){
				obj = o[namePrefix] = {};
			} 
			$.each(a, function() {
				var val = this.value === undefined ? '' : $.trim(decodeURIComponent(this.value));
				
				var name = this.name;
				
				if (obj[name] !== undefined) {
					if (!obj[name].push) {
						obj[name] = [obj[name]];
					}
					obj[name].push(val);
				} else {
					obj[name] = val;
				}
			});
			NB.sweetAlert(JSON.stringify(o));
			return o;
		},
		_ajaxForm:function(option){
			var option = $.extend({
				prefix:"form",
				callback:undefined,
				type : "GET",
				data:{},
			},option);
			var target = $(this);
			var submit = target.data('submit') ? false : true;
			target.data('submit', true);
			
			if(!submit) {
				NB.sweetAlert('不可重复提交表单！');
				return;
			}
			
			$http[option.type]($(this).attr('action'), $.extend({},target._formToJson(option.prefix),option.data), function(rep) {
				if(typeof option.callback === 'function'){
					try {
						option.callback.call(target,msg);
					} catch (e) {
						//sui.tip.danger("AJAX请求返回数据解析错误~");
						console.error('AJAX请求返回数据解析错误~['+e.message+']');
					}
				}
	        });
		}
	});
	
	that.init();
	return that;
})(jQuery);

