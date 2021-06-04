const host = 'http://127.0.0.1/ewRecord/node_modules/html/';
const webUrl = 'http://127.0.0.1/ewRecord/thinkphp/public/index.php/index/';

/*const host = 'https://www.wemark.top/ewRecord/node_modules/html/';
const webUrl = 'https://www.wemark.top/ewRecord/thinkphp/public/index.php/index/';*/

// const host = 'http://192.168.5.219:81/ewRecord/node_modules/html/';
// const webUrl = 'http://192.168.5.219:81/ewRecord/thinkphp/public/index.php/index/';

//const host='https://wechat.whgjh.top/wechat.php';
var doctor='';
const token = '';
const city = '';
const pageSize=15;//分页每页条数
const imgLoading="<img src='"+window.location.protocol+"//"+window.location.host+"/evaluate-plus/static/img/loading3.gif' style='width:55px' />"; //列表分页加载动画
const myHost=window.location.protocol+"//"+window.location.host+"/evaluate-plus";
const myHostname=window.location.hostname;
//const myHost="http://"+window.location.host;
const showFile=window.location.protocol+"//"+window.location.host+"/evaluate-plus/common/showImage?filePath=";
const appid="wxfa49a4b1a7eaa93a";
const wxUrl="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appid+"&redirect_uri="+myHost+"/teacherIndex.html&response_type=code&scope=snsapi_userinfo&state=1&connect_redirect=1#wechat_redirect";
const course={
	type:[
	{
		name:'劳动教育',
		theme:[
			{
				no:7,	
				name:'日常劳动',
				unit:[
					{name:'个人劳动'},
					{name:'家务劳动'},
					{name:'人际交往'},
			    ]
			},
			{
				no:8,
				name:'生产劳动',
				unit:[
					{name:'农事劳动'},
					{name:'设计制作'},
					{name:'生产加工'},
					{name:'工业制造'}
			    ]
			},
			{
				no:9,
				name:'服务性劳动',
				unit:[
					{name:'班级服务'},
					{name:'校园服务'},
					{name:'社会服务'}
			    ]
			}
		]
	},
	{
		name:'研学实践',
		theme:[
			{
				no:1,
				name:'传统文化',
				unit:[
					{name:'图腾祥瑞文化'},
					{name:'思想教育文化'},
					{name:'音乐戏曲文化'},
					{name:'中国书画'},
					{name:'传统技艺（含非遗文化）'},
					{name:'服饰穿戴文化'},
					{name:'生活文化（含传统节日、饮食文化、传统礼仪）'},
					{name:'中医文化'},
					{name:'中国武术'},
					{name:'中式建筑及家具装潢'},
					{name:'中式建筑及家具装潢'}
			    ]
			},
			{
				no:2,
				name:'革命传统',
				unit:[
					{name:'党的作风教育'},
					{name:'党史教育'},
					{name:'红色教育'}
			    ]
			},
			{
				no:3,
				name:'乡情国情',
				unit:[
					{name:'爱国主义教育'},
					{name:'家风教育'},
					{name:'家乡建设'},
					{name:'社会主义建设'},
					{name:'民族文化与民族团结'},
					{name:'国际视野'}
			    ]
			},
			{
				no:4,
				name:'自然生态',
				unit:[
					{name:'自然探索'},
					{name:'环境保护'}
			    ]
			},
			{
				no:5,
				name:'国防科工',
				unit:[
					{name:'国防教育'},
					{name:'科技教育'},
					{name:'现代工业探索'},
			    ]
			},
			{
				no:6,
				name:'生命教育',
				unit:[
					{name:'生命认知'},
					{name:'生存能力培养'},
					{name:'生活教育'},
			    ]
			}
		]
	}
	]
};
const activeAddress=[{'no':0,'name':'校内'},{'no':1,'name':'校外'},{'no':2,'name':'家庭'}];
const activeType=['劳动教育','研学实践'];
const grade=[{'no':1,'name':'一年级'},{'no':2,'name':'二年级'},{'no':3,'name':'三年级'},{'no':4,'name':'四年级'},
			 {'no':5,'name':'五年级'},{'no':6,'name':'六年级'},{'no':7,'name':'七年级'},{'no':8,'name':'八年级'},
			 {'no':9,'name':'九年级'},{'no':10,'name':'十年级'},{'no':11,'name':'十一年级'},{'no':12,'name':'十二年级'},];
const grades=['一年级','二年级','三年级','四年级','五年级','六年级','七年级','八年级','九年级','十年级','十一年级','十二年级'];
const area=['华北','东北','华东','华中','华南','西南','西北','台港澳']; 