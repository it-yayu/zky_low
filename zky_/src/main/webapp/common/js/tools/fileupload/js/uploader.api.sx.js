/*globals qq */
/**
 * Defines the public API for FineUploader mode by sx.
 */
(function(){
    "use strict";
    
	qq.sxUiPublicApi = {
		getTemplating_sx:function(){
			return this._templating;
		},
		getUploadData_sx:function(){
			return this._uploadData;
		},
		storeForLater_sx:function(id){
			this._parent.prototype._storeForLater.apply(this, arguments);
	        this._templating.hideSpinner(id);
		},
		onComplete_sx:function(id, name, result, xhr){
			this._onComplete(id, name, result, xhr)
			this._storedIds.pop();//去掉storeIds中的id
			this._displayFileSize(id);//显示文件大小
		},
		formatSize_sx:function(size){
			return this._formatSize(size);
		}
	}
}());

qq.extend(qq.FineUploader.prototype, qq.sxUiPublicApi);