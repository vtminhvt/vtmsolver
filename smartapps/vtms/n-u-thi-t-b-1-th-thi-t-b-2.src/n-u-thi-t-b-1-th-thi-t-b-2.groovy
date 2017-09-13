definition(
    name: "Nếu thiết bị 1 thì thiết bị 2",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Điều khiển",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")
preferences 
{
 section("Chọn")
    {
    	input("sw1","capability.switch",title:"thiết bị 1")
        input name:"sel1",type:"enum", title:"với giá trị", options: ["on","off"], defaultValue:"on"
       	
        input("sw2","capability.switch",title:"thiết bị 2")
        input name:"sel2",type:"enum", title:"với giá trị", options: ["on","off"], defaultValue:"on"
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
 subscribe(sw2,"switch",sw_2)  
}


def sw_1(evt)
{
if (evt.value == sel1)
	{
    	if (sel2=="on")
        { sw2.on()}
        
        if (sel2=="off")
        {sw2.off() }
	}
}