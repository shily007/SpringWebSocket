//分页List
function pageUI(pages,total,current){//total：总数  pages：总页数  current：当前页数
	var pageHtml="";
	app.pageHtml=imgLoading;
	if(total>0){
		var syy="";//上一页
		var dy="<span style='padding:0 10px;color:#9b9b9b'>当前第 <span style='color:green'>"+current+"</span> 页，共 <span style='color:red'>"+total+"</span> 条数据</span>"//当前页
		var xyy="";//下一页
		var goy="<input id='goPageNum' type='text' style='margin-left:10px;width:35px;padding:3px 0;text-align:center;border:1px solid #e4e4e4;border-radius:2px;font-size:15.5px;'>"+
		"<span style='color:white;padding:3px 6px;background-color:#4d94d6;;margin-left:10px;border-radius:2px' onclick='tz("+pages+")'>GO</span>";//跳转
		if(current>1){syy="<span style='color:white;padding:3px 6px;background-color:#4d94d6;border-radius:3px' onclick='beforePage("+current+")'>上一页</span>";}
		if(current<pages){xyy="<span style='color:white;padding:3px 6px;background-color:#4d94d6;border-radius:3px;font-size:14px;' onclick='nextPage("+current+")'>下一页</span>";}
		pageHtml="<div style='width:100%;font-size:15px'>"+syy+dy+xyy+goy+"</div>";
	}else{
		pageHtml="<div style='width:100%;font-size:15px'>暂无数据！</div>";
	}
	return pageHtml;
}
//上一页
function beforePage(current){
	app.current=current-1;
	app.getList(current-1);
}
//下一页
function nextPage(current){
	app.current=current+1;
	app.getList(current+1);
}
function tz(pages){
	var goPageNum=$("#goPageNum").val();
	if(goPageNum && goPageNum<=pages){
		app.getList(goPageNum);
	}else{
		alert("请输入正确的页数！");
	}
}
