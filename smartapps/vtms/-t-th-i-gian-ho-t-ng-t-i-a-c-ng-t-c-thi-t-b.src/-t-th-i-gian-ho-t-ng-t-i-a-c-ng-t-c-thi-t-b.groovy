definition(
    name: "Đặt thời gian hoạt động tối đa Công tắc/thiết bị",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Điều khiển",
    category: "Safety & Security",
   iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")

preferences 
{
    section("Kích hoạt kịch bản")
    {
        input name:"sel",type:"enum", title:"Hoạt động -on, vô hiệu -off", options: ["on","off"], defaultValue:"on"
    }
    
    
   section("Công tắc/thiết bị")
    {
        input("sw1","capability.switch",title:"Chọn Công tắc/thiết bị", multiple:true, required:true)
    } 
    
   section ("Thời gian tối đa -phút")
    {
         input name: "tmax", type: "number", title: "Nhập thời gian tối đa -phút", defaultValue:"7"
    }
          
}
def installed()
{
	init()
   
}

def updated() 
{ 
	init()
 
}

def init()
{
    subscribe(sw1,"switch",sw_1)
}

def sw_1(evt)
{
def te=tmax *60
    if (sel=="on" && evt.value=="on") 
	{
    	runIn(te,dOFF)   
    }
}

def dOFF()
{
	sw1.off()
}