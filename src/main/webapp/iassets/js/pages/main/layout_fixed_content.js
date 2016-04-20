function invoke(url){
	top.invoke(url);
}

var NB = top.NB;


$.extend( $.fn.dataTable.defaults, {
	bFilter : false,
	bLengthChange : false,
	bInfo : true,
	language: {
    	"sLengthMenu": "每页显示 _MENU_ 条记录",
		"sZeroRecords": "抱歉， 没有找到",
		"sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
		"sInfoEmpty": "没有数据",
		"sInfoFiltered": "(从 _MAX_ 条数据中检索)",
		"sZeroRecords": "没有检索到数据",
		"sSearch": "检索 : ",
		"oPaginate": {
			"sFirst": "首页",
			"sPrevious": "前一页",
			"sNext": "后一页",
			"sLast": "尾页"
		}
    }
});

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
	_formToJson:function(namePrefix,isForm){
		var o = {};
		var a = this.serializeArray();
		$.each(a, function() {
			var val = this.value === undefined ? '' : $.trim(decodeURIComponent(this.value));
			var obj;
			var name;
			if(namePrefix){
				if(isForm){
					obj = o[namePrefix] = {};
					name = this.name;
				} else {
					obj = o;
					name = namePrefix+"."+this.name;
				}
			} else {
				obj = o;
				name = this.name;
			}
			
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
		option = $.extend({
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
	},
	_dataTable : function(option){
		var callback = option.fnDrawCallback;
		option.fnDrawCallback = undefined;
		var def = {
			"fnDrawCallback":function(a){
				var opt = $(this).data("option");
				if(opt&&opt.checkbox&&opt.checkbox.enable){
					var dt = $(this).data("dt");
					dt.checkbox(true);
				}
				callback && callback();
			},
			bServerSide : true,
			sServerMethod : "POST"
		};
		var opt = $.extend(true,def,option);
		var dt = $(this)._grid(opt);
		opt = $(this).data("option");
		
		$(this).on("click", 'tbody tr', function (e) {
			$(this).toggleClass(opt.select.css);
			$(this).siblings().removeClass(opt.select.css);
		});
		
		dt.selected = function(){
			var r = dt.api().rows("." + opt.select.css);
			return dt.api().data(r);
		};
		dt.checked = function(){
			var checked = $(this).find("tr:has(input[type=checkbox]:checked)");
			var data =  dt.api().rows(checked).data();
			var d = [];
			for(var i = 0 ; i < data.length ; i ++){
				d.push(data[i]);
			}
			return d;
		};
		dt.checkbox = function(a){
			if(a){
				$(this).find("thead tr").each(function(){
					if($(this).find("th.cb").size()==0){
						$(this).prepend("<th class='cb' style='width:5%;'><input type='checkbox'></th>");
					}
				});
				$(this).find("tbody tr").each(function(){
					if($(this).find("td.cb").size()==0){
						$(this).prepend("<td class='cb'><input type='checkbox'></td>");
					}
				});
			}else{
				$(this).find("th.cb,td.cb").remove();
			}
		};
		return dt;
		
	},_grid : function(option){
		var def = {
			"select":{
				enable:true,
				css:"success",
			},
			"checkbox":{
				enable:false,
				oncheck:function(){}
			}
		};
		var opt = $.extend(true,{},def,option);
		var dt = $(this).dataTable(opt);
		$(this).data("option",opt);
		$(this).data("dt",dt);
		return dt;
	}
});