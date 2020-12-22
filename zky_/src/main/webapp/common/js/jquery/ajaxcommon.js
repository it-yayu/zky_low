var AjaxCommon = {
	_config : null,
	disabledButtons : null,
	url : null,
	data : {},
	disabled : true,
	getInstance : function(config){
		var newAjaxObj = cloneAll(this);
		var flag = newAjaxObj._init(config, true);
		if(flag){
			return newAjaxObj;
		}else{
			return null;
		}
	},
	operSuc : function(data, textStatus){
		alert(data.returnMsg);
	},
	operErr : function(data, textStatus){
		alert(data.returnMsg);
	},
	loginTimeOut : function(data, textStatus){
		alert(data.returnMsg);
	},
	sysException : function(data, textStatus){
		alert(data.returnMsg);
	},
	otherResult : function(data, textStatus){},
	butDisFunc : function(){
		if(this.disabledButtons){
			this.disabledButtons.attr("disabled","disabled");
		}
	},
	butEnFunc : function(){
		if(this.disabledButtons){
			this.disabledButtons.removeAttr("disabled");
		}
	},
	submit : function(){
		var obj = this;
		$.ajax({
			type: "POST",
			url: obj.url,
			data: obj.data,
			dataType: "json",
			timeout: 300000,
			success: function (data, textStatus, jqXHR) {
				if(data.returnCode == "1"){
					obj.operSuc(data, textStatus);
				}else if(data.returnCode == "-1"){
					obj.operErr(data, textStatus);
				}else if(data.returnCode == "-2"){
					obj.loginTimeOut(data, textStatus);
				}else if(data.returnCode == "-9"){
					obj.sysException(data, textStatus);
				}else{
					obj.otherResult(data, textStatus);
				}
			},
			error: function (XMLHttpRequest, textStatus, errorThrown) {
				if(textStatus == "timeout"){
					alert("系统忙，请稍候重试！");
				}else if(textStatus == "parsererror"){
					alert("语法结构错误！");
				}else{
					alert("发生错误！");
				}
			},
			beforeSend: function(XMLHttpRequest){
				if(obj.disabled){
					obj.butDisFunc();
				}
			},
			complete: function(XMLHttpRequest, textStatus){
				if(obj.disabled){
					obj.butEnFunc();
				}
			}
		});
	},
	_init : function(config, flag){
		if(this._config && !flag){return true;}
		this._config = config;
		//init url
		if(this._config.url){this.url = this._config.url;}else{alert("submit函数参数错误！");return false;}
		//init data
		if(this._config.data){this.data = this._config.data;}
		//init operSuc
		if(this._config.operSuc){if(typeof(this._config.operSuc) == "function"){this.operSuc = this._config.operSuc;}else{alert("submit函数参数错误！");return false;}}
		//init operErr
		if(this._config.operErr){if(typeof(this._config.operErr) == "function"){this.operErr = this._config.operErr;}else{alert("submit函数参数错误！");return false;}}
		//init loginTimeOut
		if(this._config.loginTimeOut){if(typeof(this._config.loginTimeOut) == "function"){this.loginTimeOut = this._config.loginTimeOut;}else{alert("submit函数参数错误！");return false;}}
		//init sysException
		if(this._config.sysException){if(typeof(this._config.sysException) == "function"){this.sysException = this._config.sysException;}else{alert("submit函数参数错误！");return false;}}
		//init otherResult
		if(this._config.otherResult){if(typeof(this._config.otherResult) == "function"){this.otherResult = this._config.otherResult;}else{alert("submit函数参数错误！");return false;}}
		//init butDisFunc
		if(this._config.butDisFunc){if(typeof(this._config.butDisFunc) == "function"){this.butDisFunc = this._config.butDisFunc;}else{alert("submit函数参数错误！");return false;}}
		//init butEnFunc
		if(this._config.butEnFunc){if(typeof(this._config.butEnFunc) == "function"){this.butEnFunc = this._config.butEnFunc;}else{alert("submit函数参数错误！");return false;}}
		//init disabledButtons
		if(this._config.disabledButtons){this.disabledButtons = this._config.disabledButtons;}else{this.disabledButtons = $("input:button:enabled");}
		//init disabled
		if(this._config.disabled != undefined){if(typeof(this._config.disabled) == "boolean"){this.disabled = this._config.disabled;}else{alert("submit函数参数错误！");return false;}}
		return true;
	}
};
/**
 * 分页及加载更多等工具的对象
 * 参数可在初始化对象时传入，eg：var divpage = new DivPage({url:"example url"})
 * 亦可在生成对象后修改  divpage.params = {name:"John"}
 *
 * 对象的变量名要求与传入的pagerid一致，默认为divpage
 *
 */
function DivPage(config){
	//field
	this._conifg        = {};
	this._pager         = new Pager();
	this._morer         = new Morer();
	this._pagecount     = 1;
	this.url            = "";//访问后台的URL
	this.formName       = "form1";
	this.params         = null;
	this.pagerid        = "divpage";
	this.isShowTotalPage = false;
	this.isShowTotalRecords = false;
	this.isScoll = false;
	this.isShowScollLoading = true;
	this._parameters    = null;
	this._paramFormType = true;
	this._ajaxCommon    = null;
	this.$loadingElement = null;
	this.pager = {
		firstPageText			: '首页',
		firstPageTipText		: '首页',
		lastPageText			: '尾页',
		lastPageTipText			: '尾页',
		prePageText				: '上一页',
		prePageTipText			: '上一页',
		nextPageText			: '下一页',
		nextPageTipText			: '下一页',
		totalPageBeforeText		: '共',
		totalPageAfterText		: '页',
		totalRecordsAfterText	: '条记录',
		gopageBeforeText		: '转到',
		gopageButtonOkText		: '确定',
		gopageAfterText			: '页',
		buttonTipBeforeText		: '第',
		buttonTipAfterText		: '页'
	};
	this.morer = {
		nextPageTipText : "点击加载更多",
		nextPageText    : "点击加载更多",
		noMorePage      : "没有更多了",
		loading         : "加载中"
	};
	//method
	//初始化参数
	this.init = function(config){
		if(config){
			this._config=config;
			this._pagecount = isNaN(this._config.pageNo) ? 1 : parseInt(this._config.pageNo);
			if(this._config.url){this.url = this._config.url;}
			if(this._config.formName){this.formName = this._config.formName;}
			if(this._config.params){this.params = this._config.params;}
			if(this._config.pagerid){this.pagerid = this._config.pagerid;}
			if(this._config.isShowTotalPage != undefined){this.isShowTotalPage = this._config.isShowTotalPage;}
			if(this._config.isShowTotalRecords != undefined){this.isShowTotalRecords = this._config.isShowTotalRecords;}
			if(this._config.showPageData && typeof(this._config.showPageData) == 'function'){this.showPageData = this._config.showPageData;}
			if(this._config.isScoll != undefined){if(typeof(this._config.isScoll) == "boolean"){this.isScoll = this._config.isScoll;}}
			if(this._config.isShowScollLoading != undefined){if(typeof(this._config.isShowScollLoading) == "boolean"){this.isShowScollLoading = this._config.isShowScollLoading;}}
			if(this._config.loadingElement){
				this.$loadingElement=this._config.loadingElement;
				this.$loadingElement.wrap("<div></div>");
			}
			if(this._config.pager){
				for(var key in this._config.pager){
					if(this._config.pager[key]){this.pager[key] = this._config.pager[key];}
				}
			}
			if(this._config.morer){
				for(var key in this._config.morer){
					if(this._config.morer[key]){this.morer[key] = this._config.morer[key];}
				}
			}
		}
		return this;
	};
	//查询后的回调函数
	this.showPageData = function(){return false;};
	//生成分页
	this.generatePager = function(){
		var _divpage = this;
		var _pager = this._pager;
		var n = this._pagecount;
		var pagerid = this.pagerid;
		var parameters;
		//获取第一次查询的参数及参数类型，并将参数缓存到this._parameters，类型缓存到this._paramFormType
		if(this.params){parameters = this.params;this._paramFormType = false;}
		//此类获取parameters的模式参数是name1=value1&name2=value2的字符串，且value经过两次encode
		else{parameters = encodeURI($("form[name='"+this.formName+"']").serialize());this._paramFormType = true;}	
		/*
		 *此类获取parameters的模式参数为key:value组成的json对象
		else{
			var fields = $("form[name='"+this.formName+"']").serializeArray();
			var tempparams = "{";
			$.each(fields,function(i,field){
				tempparams+="\""+field.name+"\":\""+field.value+"\"";
				if(i!=fields.length-1){
					tempparams +=",";
				}
			});
			tempparams += "}";
			parameters = $.parseJSON(tempparams);
			this._paramFormType = false;
		}
		*/
		this._parameters = parameters;
		//获得带页面的参数
		if(this._paramFormType){parameters += "&_pagecount="+n;}else{parameters._pagecount = n;}
		this._ajaxCommon = AjaxCommon.getInstance({
			url: this.url,
			data: parameters,
			operSuc: function(data,textStatus){
				if("success"==textStatus){
					if(data){
						_pager.generPageHtml({
							pagerid : pagerid,
							gopageWrapId		: pagerid+'_gopage_wrap',
							gopageButtonId		: pagerid+'_btn_go',
							gopageTextboxId		: pagerid+'_btn_go_input',
							pno : n,
							total : data.pageCount,
							totalRecords : data.rowsCount,
							mode : 'click',
							lang : _divpage.pager,
							click : function(n){
								_divpage.changePage(n);
							},
							isShowTotalPage : _divpage.isShowTotalPage,
							isShowTotalRecords : _divpage.isShowTotalRecords
						},true);
						_divpage.showPageData(data);
					}
				}
			},
			butDisFunc : function(){
				if(this.disabledButtons){
					this.disabledButtons.attr("disabled","disabled");
				}
				if(_divpage.$loadingElement && _divpage.$loadingElement.not(":hidden").size()>0){
					_divpage.$loadingElement.not(":hidden").parent("div").mask();
				}
			},
			butEnFunc : function(){
				if(this.disabledButtons){
					this.disabledButtons.removeAttr("disabled");
				}
				if(_divpage.$loadingElement){
					_divpage.$loadingElement.parent("div").unmask();
				}
			}
		});
		if(this._ajaxCommon){this._ajaxCommon.submit();}
	};
	//翻页
	this.changePage = function (n){
		var _divpage = this;
		var _pager = this._pager;
		//获取第一次查询的参数
		var parameters = this._parameters;
		if(this._paramFormType){parameters += "&_pagecount="+n;}else{parameters._pagecount = n;}
		this._ajaxCommon.data = parameters;
		this._ajaxCommon.operSuc = function(data, textStatus){
			if("success"==textStatus){
				if(data){
					_pager._config['total'] = isNaN(data.pageCount) ? 1 : parseInt(data.pageCount);
					_pager._config['totalRecords'] = isNaN(data.rowsCount) ? 0 : parseInt(data.rowsCount);
					_pager.selectPage(n);
					
					return _divpage.showPageData(data);
				}
			}
		};
		if(this._ajaxCommon){this._ajaxCommon.submit();}
	};
	this.generateGetMore = function(){
		var _divpage = this;
		var _morer = this._morer;
		var n = this._pagecount;
		var pagerid = this.pagerid;
		var parameters;
		this._parameters = parameters;
		if(this._paramFormType){parameters += "&_pagecount="+n;}else{parameters._pagecount = n;}
		this._ajaxCommon = AjaxCommon.getInstance({
			url: this.url,
			data: parameters,
			operSuc: function(data,textStatus){
				if("success"==textStatus){
					if(data){
						_morer.generateHtml({
							divid : pagerid,
							pno : n,
							total : data.pageCount,
							totalRecords : data.rowsCount,
							isScoll : _divpage.isScoll,
							lang: _divpage.morer,
							click : function(n){
								_divpage.getMore(n);
							}
						});
						_divpage.showPageData(data);
						if(_divpage.isScoll){
							_morer._interval = setInterval(_divpage.pagerid+"._morer.hasScoll()",500);
						}
					}
				}
			},
			butDisFunc : function(){
				if(this.disabledButtons){
					this.disabledButtons.attr("disabled","disabled");
				}
				if(_divpage.$loadingElement && _divpage.$loadingElement.not(":hidden").size()>0){
					_divpage.$loadingElement.not(":hidden").parent("div").mask();
				}
				if(_divpage.isShowScollLoading){
					_morer.loading();
				}
			},
			butEnFunc : function(){
				if(this.disabledButtons){
					this.disabledButtons.removeAttr("disabled");
				}
				if(_divpage.$loadingElement){
					_divpage.$loadingElement.parent("div").unmask();
				}
			}
		});
		if(this._ajaxCommon){this._ajaxCommon.submit();}
	};
	this.getMore = function(n){
		var _divpage = this;
		var _morer = this._morer;
		//获取第一次查询的参数
		var parameters = this._parameters;
		if(this._paramFormType){parameters += "&_pagecount="+n;}else{parameters._pagecount = n;}
		this._ajaxCommon.data = parameters;
		this._ajaxCommon.operSuc = function(data, textStatus){
			if("success"==textStatus){
				if(data){
					_morer._config['total'] = data.pageCount;
					_morer._config['totalRecords'] = data.rowsCount;
					_morer.selectPage(n);
					
					_divpage.showPageData(data);
					
					if(_divpage.isScoll){
						_divpage._morer._isThreadSleep = false;
					}
					return;
				}
			}
		};
		if(this._ajaxCommon){this._ajaxCommon.submit();}
	};
	
	
	//return
	return this.init(config);
}
/**
 *	点击加载更多对象
 */
function Morer(){
    this._config = null;
	this.divid = 'getMore';
	this.pno = 1;
	this.total = 1;
	this.totalRecords = 0;
	this.isScoll = false;
	this._isThreadSleep = false;
	this._interval = null;
	this.lang = {};
	this.init = function(config){
		if(config){
			this._config=config;
			if(this._config.divid){this.divid = this._config.divid;}
			this.pno = isNaN(this._config.pno) ? 1 : parseInt(this._config.pno);
			this.total = isNaN(this._config.total) ? 1 : parseInt(this._config.total);
			this.totalRecords = isNaN(this._config.totalRecords) ? 1 : parseInt(this._config.totalRecords);
			
			if(this._config.isScoll != undefined){if(typeof(this._config.isScoll) == "boolean"){this.isScoll = this._config.isScoll;}}
			if(this._config.lang){
				for(var key in this._config.lang){
					this.lang[key] = this._config.lang[key];
				}
			}
			if(this._config.click && typeof(this._config.click) == 'function'){this.click = this._config.click;}
			this.hasNext = (this.pno < this.total);
			this.next = (this.pno >= this.total-1) ? this.total : (this.pno + 1);
			
		}
	};
	this.generateHtml = function(config){
		if(config){
			this.init(config);
		}
		
		$("#"+this.divid).addClass("more_div");
		
		//没有下一页
		if(!this.hasNext){
			str = '<span class="more_disabled">'+this.lang.noMorePage+'</span>';
			$("#"+this.divid).html(str);
			return;
		}
		//有下页，切是滚动加载
		if(this.isScoll){
			var _morerObj = this;
			
			$(window).scroll(function(){ 
				if(!_morerObj.hasNext){
					return;
				}
				if(_morerObj._isThreadSleep){
					return;
				}
				var bodyScrollTop = document.body.scrollTop;    //浏览器已滚动的高度
				var windowHeight = $(window).height();          //窗口高度
				var offset = $("#"+_morerObj.divid).offset();    
				
				if((bodyScrollTop+windowHeight)>offset.top){
					_morerObj._isThreadSleep = true;
					_morerObj._clickHandler(_morerObj.next);
				}
			});
		}
		else{//有下页，点击加载
			var str = '<a '+this._getHandlerStr(this.next)+' class="more_enabled" title="'
					+(this.lang.nextPageTipText || this.lang.nextPageText)+'">'+this.lang.nextPageText+'</a>';
					
			$("#"+this.divid).html(str);
		}
	};
	this._getHandlerStr = function(n){
		return 'href="'+this.getHref(n)+'" onclick="return '+this.divid+'._morer._clickHandler('+n+')"';
	};
	this.getHref = function(n){
		return '#';
	};
	this._clickHandler = function(n){
		var res = false;
		if(this.click && typeof this.click == 'function'){
			res = this.click.call(this,n) || false;
		}
		return res;
	};
	this.click = function(n){
		return false;
	};
	this.selectPage = function(n){
		this._config['pno'] = n;
		this.generateHtml(this._config);
	};
	this.loading = function(){
		var str_load = '<div class="more_disabled"><span class="more_loading" >&nbsp;&nbsp;&nbsp;</span><span>'+this.lang.loading+'</span></div>';
		$("#"+this.divid).html(str_load);
	};
	this.hasScoll = function(){
		var windowHeight = $(window).height();          //窗口高度
		var offset = $("#"+this.divid).offset();
		if(this._isThreadSleep){
			return;
		}
		if(windowHeight<offset.top){
			clearInterval(this._interval);
			return;
		}
		if(!this.hasNext){
			clearInterval(this._interval);
			return;
		}
		this._isThreadSleep = true;
		this._clickHandler(this.next);
		
	};
}
/**
 *	分页对象
 */
function Pager(){
	this.pagerid 			= 'divpage'; //divID
	this.mode				= 'link'; //模式(link 或者 click)
	this.pno				= 1; //当前页码
	this.total				= 1; //总页码
	this.totalRecords		= 0; //总数据条数
	this.isShowFirstPageBtn	= true; //是否显示首页按钮
	this.isShowLastPageBtn	= true; //是否显示尾页按钮
	this.isShowPrePageBtn	= true; //是否显示上一页按钮
	this.isShowNextPageBtn	= true; //是否显示下一页按钮
	this.isShowTotalPage 	= true; //是否显示总页数
	this.isShowTotalRecords = true; //是否显示总记录数
	this.isGoPage 			= true;	//是否显示页码跳转输入框
	this.hrefFormer			= ''; //链接前部
	this.hrefLatter			= ''; //链接尾部
	this.gopageWrapId		= 'kkpager_gopage_wrap';
	this.gopageButtonId		= 'kkpager_btn_go';
	this.gopageTextboxId	= 'kkpager_btn_go_input';
	this.lang				= {};
	//链接算法（当处于link模式）,参数n为页码
	this.getLink = function(n){
		if(n == 1){
			return this.hrefFormer + this.hrefLatter;
		}
		return this.hrefFormer + '_' + n + this.hrefLatter;
	};
	//页码单击事件处理函数（当处于mode模式）,参数n为页码
	this.click = function(n){
		//这里自己实现
		return false;
	};
	//获取href的值（当处于mode模式）,参数n为页码
	this.getHref = function(n){
		//默认返回'#'
		return '#';
	};
	//跳转框得到输入焦点时
	this.focus_gopage = function (){
		var btnGo = $('#'+this.gopageButtonId);
		$('#'+this.gopageTextboxId).attr('hideFocus',true);
		btnGo.show();
		btnGo.css('left','0px');
		$('#'+this.gopageWrapId).css('border-color','#6694E3');
		btnGo.animate({left: '+=44'}, 50,function(){
			//$('#'+this.gopageWrapId).css('width','88px');
		});
	};
	//跳转框失去输入焦点时
	this.blur_gopage = function(){
		var _this = this;
		setTimeout(function(){
			var btnGo = $('#'+_this.gopageButtonId);
			btnGo.animate({
			    left: '-=44'
			  }, 100, function(){
				  btnGo.css('left','0px');
				  btnGo.hide();
				  $('#'+_this.gopageWrapId).css('border-color','#DFDFDF');
			  });
		},400);
	};
	//跳转输入框按键操作
	this.keypress_gopage = function(){
		var event = arguments[0] || window.event;
		var code = event.keyCode || event.charCode;
		//delete key
		if(code == 8) return true;
		//enter key
		if(code == 13){
			this.gopage();
			return false;
		}
		//copy and paste
		if(event.ctrlKey && (code == 99 || code == 118)) return true;
		//only number key
		if(code<48 || code>57)return false;
		return true;
	};
	//跳转框页面跳转
	this.gopage = function(){
		var str_page = $('#'+this.gopageTextboxId).val();
		if(isNaN(str_page)){
			$('#'+this.gopageTextboxId).val(this.next);
			return;
		}
		var n = parseInt(str_page);
		if(n < 1) n = 1;
		if(n > this.total) n = this.total;
		if(this.mode == 'click'){
			this._clickHandler(n);
		}else{
			window.location = this.getLink(n);
		}
	};
	//不刷新页面直接手动调用选中某一页码
	this.selectPage = function(n){
		this._config['pno'] = n;
		this.generPageHtml(this._config,true);
	};
	//生成控件代码
	this.generPageHtml = function(config,enforceInit){
		if(enforceInit || !this.inited){
			this.init(config);
		}
		
		var str_first='',str_prv='',str_next='',str_last='';
		if(this.isShowFirstPageBtn){
			if(this.hasPrv){
				str_first = '<a '+this._getHandlerStr(1)+' title="'
					+(this.lang.firstPageTipText || this.lang.firstPageText)+'">'+this.lang.firstPageText+'</a>';
			}else{
				str_first = '<span class="disabled">'+this.lang.firstPageText+'</span>';
			}
		}
		if(this.isShowPrePageBtn){
			if(this.hasPrv){
				str_prv = '<a '+this._getHandlerStr(this.prv)+' title="'
					+(this.lang.prePageTipText || this.lang.prePageText)+'">'+this.lang.prePageText+'</a>';
			}else{
				str_prv = '<span class="disabled">'+this.lang.prePageText+'</span>';
			}
		}
		if(this.isShowNextPageBtn){
			if(this.hasNext){
				str_next = '<a '+this._getHandlerStr(this.next)+' title="'
					+(this.lang.nextPageTipText || this.lang.nextPageText)+'">'+this.lang.nextPageText+'</a>';
			}else{
				str_next = '<span class="disabled">'+this.lang.nextPageText+'</span>';
			}
		}
		if(this.isShowLastPageBtn){
			if(this.hasNext){
				str_last = '<a '+this._getHandlerStr(this.total)+' title="'
					+(this.lang.lastPageTipText || this.lang.lastPageText)+'">'+this.lang.lastPageText+'</a>';
			}else{
				str_last = '<span class="disabled">'+this.lang.lastPageText+'</span>';
			}
		}
		var str = '';
		var dot = '<span>...</span>';
		var total_info='';
		if(this.isShowTotalPage || this.isShowTotalRecords){
			total_info = '&nbsp;<span class="normalsize">'+this.lang.totalPageBeforeText;
			if(this.isShowTotalPage){
				total_info += this.total + this.lang.totalPageAfterText;
				if(this.isShowTotalRecords){
					total_info += '/';
				}
			}
			if(this.isShowTotalRecords){
				total_info += this.totalRecords + this.lang.totalRecordsAfterText;
			}
			
			total_info += '</span>';
		}
		
		var gopage_info = '';
		if(this.isGoPage){
			gopage_info = '&nbsp;'+this.lang.gopageBeforeText+'<span id="'+this.gopageWrapId+'" class="divpage_gopage_wrap">'+
				'<input type="button" id="'+this.gopageButtonId+'" class="divpage_btn_go" onclick="'+this.pagerid+'._pager.gopage()" value="'
					+this.lang.gopageButtonOkText+'" />'+
				'<input type="text" id="'+this.gopageTextboxId+'" class="divpage_btn_go_input" onfocus="'+this.pagerid+'._pager.focus_gopage()"  onkeypress="return \n'+this.pagerid+'._pager.keypress_gopage(event);"   onblur="'+this.pagerid+'._pager.blur_gopage()" value="'+this.next+'" /></span>'+this.lang.gopageAfterText;
		}
		
		//分页处理
		if(this.total <= 8){
			for(var i=1;i<=this.total;i++){
				if(this.pno == i){
					str += '<span class="curr">'+i+'</span>';
				}else{
					str += '<a '+this._getHandlerStr(i)+' title="'
						+this.lang.buttonTipBeforeText + i + this.lang.buttonTipAfterText+'">'+i+'</a>';
				}
			}
		}else{
			if(this.pno <= 5){
				for(var i=1;i<=7;i++){
					if(this.pno == i){
						str += '<span class="curr">'+i+'</span>';
					}else{
						str += '<a '+this._getHandlerStr(i)+' title="'+
							this.lang.buttonTipBeforeText + i + this.lang.buttonTipAfterText+'">'+i+'</a>';
					}
				}
				str += dot;
			}else{
				str += '<a '+this._getHandlerStr(1)+' title="'
					+this.lang.buttonTipBeforeText + '1' + this.lang.buttonTipAfterText+'">1</a>';
				str += '<a '+this._getHandlerStr(2)+' title="'
					+this.lang.buttonTipBeforeText + '2' + this.lang.buttonTipAfterText +'">2</a>';
				str += dot;
				
				var begin = this.pno - 2;
				var end = this.pno + 2;
				if(end > this.total){
					end = this.total;
					begin = end - 4;
					if(this.pno - begin < 2){
						begin = begin-1;
					}
				}else if(end + 1 == this.total){
					end = this.total;
				}
				for(var i=begin;i<=end;i++){
					if(this.pno == i){
						str += '<span class="curr">'+i+'</span>';
					}else{
						str += '<a '+this._getHandlerStr(i)+' title="'
							+this.lang.buttonTipBeforeText + i + this.lang.buttonTipAfterText+'">'+i+'</a>';
					}
				}
				if(end != this.total){
					str += dot;
				}
			}
		}
		
		str = "&nbsp;" + str_first + str_prv + str + str_next + str_last + total_info + gopage_info;
		$("#"+this.pagerid).addClass("divpage");
		$("#"+this.pagerid).html(str);
	};
	//分页按钮控件初始化
	this.init = function(config){
		this.pno = isNaN(config.pno) ? 1 : parseInt(config.pno);
		this.total = isNaN(config.total) ? 1 : parseInt(config.total);
		this.totalRecords = isNaN(config.totalRecords) ? 0 : parseInt(config.totalRecords);
		if(config.pagerid){this.pagerid = config.pagerid;}
		if(config.mode){this.mode = config.mode;}
		if(config.gopageWrapId){this.gopageWrapId = config.gopageWrapId;}
		if(config.gopageButtonId){this.gopageButtonId = config.gopageButtonId;}
		if(config.gopageTextboxId){this.gopageTextboxId = config.gopageTextboxId;}
		if(config.isShowFirstPageBtn != undefined){this.isShowFirstPageBtn=config.isShowFirstPageBtn;}
		if(config.isShowLastPageBtn != undefined){this.isShowLastPageBtn=config.isShowLastPageBtn;}
		if(config.isShowPrePageBtn != undefined){this.isShowPrePageBtn=config.isShowPrePageBtn;}
		if(config.isShowNextPageBtn != undefined){this.isShowNextPageBtn=config.isShowNextPageBtn;}
		if(config.isShowTotalPage != undefined){this.isShowTotalPage=config.isShowTotalPage;}
		if(config.isShowTotalRecords != undefined){this.isShowTotalRecords=config.isShowTotalRecords;}
		if(config.isGoPage != undefined){this.isGoPage=config.isGoPage;}
		if(config.lang){
			for(var key in config.lang){
				this.lang[key] = config.lang[key];
			}
		}
		this.hrefFormer = config.hrefFormer || '';
		this.hrefLatter = config.hrefLatter || '';
		if(config.getLink && typeof(config.getLink) == 'function'){this.getLink = config.getLink;}
		if(config.click && typeof(config.click) == 'function'){this.click = config.click;}
		if(config.getHref && typeof(config.getHref) == 'function'){this.getHref = config.getHref;}
		if(!this._config){
			this._config = config;
		}
		//validate
		if(this.pno < 1) this.pno = 1;
		this.total = (this.total <= 1) ? 1: this.total;
		if(this.pno > this.total) this.pno = this.total;
		this.prv = (this.pno<=2) ? 1 : (this.pno-1);
		this.next = (this.pno >= this.total-1) ? this.total : (this.pno + 1);
		this.hasPrv = (this.pno > 1);
		this.hasNext = (this.pno < this.total);
		
		this.inited = true;
	};
	this._getHandlerStr = function(n){
		if(this.mode == 'click'){
			return 'href="'+this.getHref(n)+'" onclick="return '+this.pagerid+'._pager._clickHandler('+n+')"';
		}
		//link模式，也是默认的
		return 'href="'+this.getLink(n)+'"';
	};
	this._clickHandler = function(n){
		var res = false;
		if(this.click && typeof this.click == 'function'){
			res = this.click.call(this,n) || false;
		}
		return res;
	};
}
/**
 *	深克隆方法
 */
function cloneAll(org){
	var dest = {};
	for(var i in org){
		if(typeof org[i] == "object" && org[i] != null){
			dest[i] = cloneAll(org[i]);
			continue;
		}
		dest[i] = org[i];
	}
	return dest;
}

/**
 *	加载中
 */
(function($){
	$.fn.mask = function(){
		$(this).each(function() {
			$.maskElement($(this));
		});
	};
	
	$.fn.unmask = function(){
		$(this).each(function() {
			$.unmaskElement($(this));
		});
	};
	
	$.fn.isMasked = function(){
		return this.hasClass("loadmasked");
	};

	$.maskElement = function(element){
		if(element.isMasked()) {
			$.unmaskElement(element);
		}
		
		if(element.css("position") == "static") {
			element.addClass("loadmasked-relative");
		}
		
		element.addClass("loadmasked");
		
		var maskDiv = $('<div class="loadingmask"></div>');
		
		//auto height fix for IE
		if(navigator.userAgent.toLowerCase().indexOf("msie") > -1){
			maskDiv.height(element.height() + parseInt(element.css("padding-top")) + parseInt(element.css("padding-bottom")));
			maskDiv.width(element.width() + parseInt(element.css("padding-left")) + parseInt(element.css("padding-right")));
		}
		
		//fix for z-index bug with selects in IE6
		if(navigator.userAgent.toLowerCase().indexOf("msie 6") > -1){
			element.find("select").addClass("loadmasked-hidden");
		}
		
		element.append(maskDiv);
		
		var maskMsgDiv = $('<div class="loadingmask-msg" style="display:none;"></div>');
		maskMsgDiv.append('<div>loading</div>');
		element.append(maskMsgDiv);
		
		//calculate center position
		maskMsgDiv.css("top", Math.round(element.height() / 2 - (maskMsgDiv.height() - parseInt(maskMsgDiv.css("padding-top")) - parseInt(maskMsgDiv.css("padding-bottom"))) / 2)+"px");
		maskMsgDiv.css("left", Math.round(element.width() / 2 - (maskMsgDiv.width() - parseInt(maskMsgDiv.css("padding-left")) - parseInt(maskMsgDiv.css("padding-right"))) / 2)+"px");
		
		maskMsgDiv.show();
	};
	
	$.unmaskElement = function(element){
		element.find(".loadingmask-msg,.loadingmask").remove();
		element.removeClass("loadmasked");
		element.removeClass("loadmasked-relative");
		element.find("select").removeClass("loadmasked-hidden");
	};
	
	/**
	 *	jQuery对象增加addTD方法
	 */
	$.fn.addTD = function(value,attr){
		$(this).each(function() {
			var $td = $("<td/>").attr("align","center").html(value);
			if(attr && attr instanceof Object){
				$.each(attr,function(name,value){
					$td.attr(name,value);
				});
			}
			$(this).append($td);
		});
		return this;
	};
})(jQuery);