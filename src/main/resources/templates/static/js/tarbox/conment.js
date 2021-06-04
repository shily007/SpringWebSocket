//
//编辑浮框
function edit(e,mid){
	var that=this;
	$(".bak0").show();
	$(".bak1").show(200);
	if(mid){
		app.List.some((v,i)=>{
	        if(v.id==mid || v.userId==mid){ //userId 教师、学生特殊情况
	          app.Info=app.List[i];
	          if(app.List[i].gradeStr){
	        	  boxArray('allGrade',app.List[i].gradeStr);
	        	  app.getTheme();app.getUnit();
	          }
	          return true;
	        }
		})
	}else{
		app.Info={subjectIds:[],gender:'',majorId:'',gradeId:[],headTeacherId:''};//新增则置空Info
	}
}
function editWx(e,mid){
	var that=this;
	if(mid){
		app.List2.teacherOverQuest.some((v,i)=>{
	        if(v.questionBankId==mid){ //教师端回显
	          app.Info=app.List2.teacherOverQuest[i];
	          boxArray('studentList',app.Info.studentIds);
	          return true;
	        }
		})
	}else{
		app.Info={students:[],imagePath:'',videoPath:''};//新增则置空Info
	}
}

function allChoose(e){//全选、反选
	if($(e).is(":checked")){
		$(e).parent().parent().find("input[type='checkbox']").prop("checked",true);
	}else{
		$(e).parent().parent().find("input[type='checkbox']").prop("checked",false);
	}
}

function manyValues() {
	  var url=decodeURI(window.location.search);
	  if(url.indexOf("?")!=-1) {
	      var str = url.substr(1);
	      strs = str.split("&"); 
	      var key = new Array(strs.length);
	      var value = new Array(strs.length);
	      for(i = 0;i < strs.length;i++){
	          key[i] = strs[i].split("=")[0]
	          value[i] = unescape(strs[i].split("=")[1]); 
	          //alert(key[i]+"="+value[i]);
	      }
	      var data=[];
	      data.push({'key':key,'value':value});
	      return data[0];
	  }else{
		  return '';
	  }
	}

/*********************   POST  ****************************/
function htpPost(url,data,type,toUrl){//post提交数据
	parent.showLoading();
	axios.post(url,data,{
    	headers: {
    		'Content-Type': 'application/x-www-form-urlencoded',
        	'Authorization':'Bearer '+$.cookie('access_token'),
    		}
    })
    .then(function(res){
    	console.log(res);
    	parent.hideLoading();
    	if(res.data.success){
    		if(type==0){alert("操作成功！");location.reload();}//刷新页面
    		else if(type==3){app.getList();}//刷新数据，不刷新页面
    		else if(type==99){alert("操作成功！");outLogin();}//备用：修改密码
    		else if(type==2){app.parentId=res.data.data.id;alert("操作成功！");}//后续操作单独添加
    		else if(type==4){alert("操作成功！");location.href=toUrl;}//操作成功后跳转url
    		else if(type==1){app.divNum=app.divNum+1;app.Info=res.data.data;app.reloadHtml();}//后续操作,重载页面
    		else if(type==5){app.wx=res.data.data;}//
    		else if(type==6){app.getClassTeacherInfo();app.divNum=app.divNum+1;app.reloadHtml();}//活动特殊
    		else if(type==7){
    			var data = Qs.stringify({'activityId':app.Info.id,'state':app.state});
    			htpPost(myHost+'/activity/release',data,4,"../../organ/activeManagement/activeList.html");
    		}
    	}else{alert(res.data.msg);}
    })
    .catch(function (error) {
    	parent.hideLoading();
        console.log(error);
    });
}

function htpPostFile(url,data,type,toUrl){//post提交文件至后台
	parent.showLoading();
	axios.post(url,data,{
    	headers: {
    		'Content-Type': 'multipart/form-data',
        	'Authorization':'Bearer '+$.cookie('access_token'),
    		}
    })
    .then(function(res){
    	console.log(res);
    	parent.hideLoading();
    	if(res.data.success){
    		if(type==0){alert("操作成功！");location.reload();}//刷新页面
    		else if(type==3){app.getList();}//刷新数据，不刷新页面
    		else if(type==99){alert("操作成功！");outLogin();}//备用：修改密码
    		else if(type==2){app.parentId=res.data.data.id;alert("操作成功！");}//后续操作单独添加
    		else if(type==4){alert("操作成功！");location.href=toUrl;}//操作成功后跳转url
    	}else{alert(res.data.msg);}
    })
    .catch(function (error) {
    	parent.hideLoading();
        console.log(error);
    });
}
function htpWxPostFile(url,data,type,toUrl){//微信端 post提交文件至后台
	axios.post(url,data,{
    	headers: {
    		'Content-Type': 'multipart/form-data',
        	'Authorization':'Bearer '+$.cookie('access_token'),
    		}
    })
    .then(function(res){
    	console.log(res);
    	if(type==1){app.Info.img=res.data;}
    	else if(type==2){app.Info.videoPath=res.data.data;}//返回图片路径
    })
    .catch(function (error) {
    	parent.hideLoading();
        console.log(error);
    });
}
/*********************   PUT  ****************************/
function htpPut(url,data,type,toUrl){//put提交
	axios.put(url,data,{
    	headers: {
    		'Content-Type': 'application/x-www-form-urlencoded',
        	'Authorization':'Bearer '+$.cookie('access_token'),
    		}
    })
    .then(function(res){
    	console.log(res);
    	if(res.data.success){
    		if(type==1){app.getInfo();app.divNum=app.divNum+1;app.reloadHtml();}//活动特殊
    		else if(type==2){alert("修改成功！");location.href=toUrl;}//课程特殊
    		else{
    			alert("操作成功！");closeMenu();app.getList();
    		}  		
    	}else{alert(res.data.msg);}
    })
    .catch(function (error) {
    	parent.hideLoading();
        console.log(error);
    });
}
/*********************   GET  ****************************/
function htpGet(url,data,type){//get提交获取分页列表
	axios.get(url,{headers: {
		'Content-Type': 'application/x-www-form-urlencoded',
    	'Authorization':'Bearer '+$.cookie('access_token'),
		},
		params:data
	}).then(function(res){		
		if(res.data.success){
			if(type==0){//分页
				console.log(res);
				app.pageHtml=pageUI(res.data.data.pages,res.data.data.total,res.data.data.current);
				app.List=res.data.data.records;
			}else if(type==1){app.List1=res.data.data;}
			else if(type==2){app.List2=res.data.data;}
			else if(type==3){app.List3=res.data.data;}
			else if(type==4){app.List4=res.data.data;}
			else if(type==5){app.List5=res.data.data;}
			else if(type==6){app.List6=res.data.data;app.pageHtml="";}//列表加载
			else if(type==7){app.List7=res.data.data;}
			else if(type==8){app.List8=res.data.data;}
			else if(type==9){app.List9=res.data.data;}
			else if(type==10){app.List10=res.data.data;app.getInfo();}//
			else if(type==11){app.List1=res.data.data;app.readData();}
			else if(type==12){app.List=res.data.data;app.pageHtml="";}//列表加载菜单应用
			else if(type==13){app.List=res.data.data.records;}//教师端分页数据
			else if(type==14){app.List2=res.data.data;app.answerCheck();}//教师端分页数据
			else if(type==99){if(res.data.success){alert("操作成功！");}}//提交数据
		}
	}).catch(function (error) {
		if(error=="Error: Request failed with status code 401"){alert("登录超时，请重新登录！");parent.outLogin();}
		console.log(error);
	});
}
/*********************   GET Info ****************************/
function htpGetInfo(url,data,type){//get提交详情
	axios.get(url,{headers: {
		'Content-Type': 'application/x-www-form-urlencoded',
    	'Authorization':'Bearer '+$.cookie('access_token'),
		},
		params:data
	}).then(function(res){
		console.log(res);
		if(res.data.success){
			if(type==1){
				app.Info=res.data.data;	            
	            app.getList();
	            app.divNum=1;
	            app.reloadNum();
			}else if(type==2){//课程专属
				app.Info=res.data.data;
				app.getTheme();
				app.getUnit();
				app.getGoalList();
			}else if(type==99){
				app.wx=res.data.data;	   
			}else if(type==3){app.Info=res.data.data;$.cookie("state",res.data.data.state,{expires:1});app.state=res.data.data.state;}//更新state
			else{app.Info=res.data.data;}
		}
	}).catch(function (error) {
		console.log(error);
	});
}
/*********************   Delete  ****************************/
function htpDeleteDel(url,data){//get提交详情
	axios.delete(url,{headers: {
		'Content-Type': 'application/x-www-form-urlencoded',
    	'Authorization':'Bearer '+$.cookie('access_token'),
		},
		params:data
	}).then(function(res){
		console.log(res);
		if(res.data.success){
			app.getList();
			alert("操作成功！");
			//app.Info=res.data.data;
		}else{alert(res.data.msg);}
	}).catch(function (error) {
		console.log(error);
	});
}
function getPulicKey(){//获取公钥加密可用
	axios.get(myHost+'/public/key',{params:{}}).then(function(res){
		app.publicKey=res.data.data;
	}).catch(function (error) {
        console.log(error);
    });
}
//关闭菜单
function closeMenu(){
	$(".bak0").hide();
	$(".bak1").hide(200);
	app.Info={subjectIds:[],gender:'',majorId:'',gradeId:'',headTeacherId:''};
}
//wx++++++++++++++++++++++++++++++++++++微信
/*********************   POST  ****************************/
function htpPostWx(url,data,type,toUrl){//post提交数据
	axios.post(url,data,{
    	headers: {
    		'Content-Type': 'application/x-www-form-urlencoded',
        	'Authorization':'Bearer '+$.cookie('access_token'),
    		}
    })
    .then(function(res){
    	console.log(res);
    	if(res.data.success){
    		if(type==0){alert("操作成功！");location.reload();}//刷新页面
    		else if(type==3){app.getList();}//刷新数据，不刷新页面
    		else if(type==99){alert("操作成功！");outLogin();}//备用：修改密码
    		else if(type==2){app.parentId=res.data.data.id;alert("操作成功！");}//后续操作单独添加
    		else if(type==4){alert("操作成功！");$.cookie("teacherId",res.data.data.id,{expires:1});
   			$.cookie("state",res.data.data.state,{expires:1});location.href=toUrl;}//操作成功后跳转url
    		else if(type==1){app.divNum=app.divNum+1;app.reloadHtml();}//后续操作,重载页面
    		else if(type==5){app.wx=res.data.data;}//
    		else if(type==6){alert("操作成功！");app.closeS();}//
    		else if(type==7){alert("操作成功！");location.href=toUrl;}//操作成功后跳转url
    	}else{alert(res.data.msg);}
    })
    .catch(function (error) {
        console.log(error);
    });
}

//数字转汉字
function convertToChinese(num){
	var N = ["零", "一", "二", "三", "四", "五", "六", "七", "八", "九"];
    var str = num.toString();
    var len = num.toString().length;
    var C_Num = [];
    for(var i = 0; i < len; i++){
        C_Num.push(N[str.charAt(i)]);
    }
    return C_Num.join('');
}
//获取当前年份往前推算12个自然年作为年级名称
function yearGrade(){
	var date = new Date();
	var year = date.getFullYear();
	var years=[];
	for(var i=12;i>0;i--){
		years[12-i]=year;
		year--;
	}
	return years;
}
//将某个对象下面的已选择的checkbox值转化为string已逗号的形式组成一个字符串
function boxString(object,type){
	var stringO="";
	var num=0;
	$("#"+object).find("input[type='checkbox']").each(function(){
		if($(this).is(":checked")){
			if(type==1){//二级指标特殊
				if($(this).attr("rel")){
					if(num==0){stringO=stringO+$(this).attr("rel");}else{stringO=stringO+"^"+$(this).attr("rel");}
					num++;
				}
			}else{
				if($(this).val()){
					if(num==0){stringO=stringO+$(this).val();}else{stringO=stringO+","+$(this).val();}
					num++;
				}
			}
		}
	})
	return stringO;
}
//回显选中checkbox
function boxArray(object,data,type){
	$("#"+object).find("input[type='checkbox']").prop("checked",false);//先初始化选中状态
	if(data){
		if(type==1){//二级指标特殊回显
			var dataA=data.split("^");
			for(var i=0;i<dataA.length;i++){
				$("#"+object).find("input[type='checkbox'][rel='"+dataA[i]+"']").prop("checked",true);
			}
		}else{
			var dataA=data.split(",");
			for(var i=0;i<dataA.length;i++){
				$("#"+object).find("input[type='checkbox'][value='"+dataA[i]+"']").prop("checked",true);
			}
		}
	}
}