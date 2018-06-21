definition(
    name: "Thông báo các hoạt động của công tắc điện, thiết bị điện",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Trình điều khiển, giao tiếp, ứng dụng cơ bản",
    category: "Safety & Security",
    iconUrl: "https://s3.amazonaws.com/vtmsmartthings/vtms60.png",
    iconX2Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png",
    iconX3Url: "https://s3.amazonaws.com/vtmsmartthings/vtms120.png")


preferences {
    section("Kích hoạt kịch bản")
    {
        input name:"sel",type:"enum", title:"Hoạt động", options: ["on","off"], defaultValue:"off"
    }
    section("Chọn khoảng thời gian")
    {
     input name: "timeB", type: "time", title: "Đặt thời gian bắt đầu nhận thông báo"
     input name: "timeE", type: "time", title: "Và thời gian kết thúc"
    }
  
    section("Công tắc điện")
    {
    	input("sw1","capability.switch",title:"Chọn công tắc điện bạn muốn điều khiển")     
    }
    section("Nội dung hoạt động")
    	{
        	input name:"txt",type:"text", title:"Nhập nội dung",defaultValue:" "
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
def timeofB = timeToday(timeB)
def timeofE= timeToday(timeE)
def timeC=	now()

	def dk1= (timeofB<timeofE) && (timeC >= timeofB.time && timeC<=timeofE.time)
    def dk2= (timeofB>timeofE) && (timeC >= timeofB.time || timeC<=timeofE.time)
    def dk= (sel == "on")&& (dk1 || dk2)
    if (dk)
    {
        if(evt.value=="on") 
        {
            tb("${evt.displayName} đang mở/on")
        }
        else 	
        {
            tb("${evt.displayName} đã tắt/off")
       }
   }
}

def tb(msg)
{
	sendPush(msg)
}