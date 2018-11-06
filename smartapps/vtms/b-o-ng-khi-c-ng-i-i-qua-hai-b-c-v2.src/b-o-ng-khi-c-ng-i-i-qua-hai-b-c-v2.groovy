definition(
    name: "Báo động khi có người đi qua hai bước V2",
    namespace: "VTMS",
    author: "Võ Thanh Minh",
    description: "Kịch bản điều khiển thiết bị dựa vào thói quen, sở thích và mệnh lệnh",
    category: "Safety & Security",
    iconUrl: "https://i.imgur.com/f73vWMD.png",
    iconX2Url: "https://i.imgur.com/f73vWMD.png",
    iconX3Url: "https://i.imgur.com/f73vWMD.png")
//Test:OK 

preferences 
{
    section("Kích hoạt kịch bản")
    {
        input name:"sel",type:"enum", title:"Hoạt động", options: ["on","off"], defaultValue:"off"
    }
    
    section("Chọn khoảng thời gian")
    {
         input name: "timeB", type: "time", title: "Đặt thời gian bắt đầu",defaultValue:"23:59"
         input name: "timeE", type: "time", title: "Đặt thời gian kết thúc",defaultValue:"4:00"
    }
    
    section("Chuyển động ở đâu")
    {
        input("motionCD", "capability.motionSensor",title:"Chọn cảm biến chuyển động")
    }
    
    section ("Thời gian báo động lần 1")
    {
         input name: "tp1", type: "number", title: "Báo động trong bao lâu(giây)?", defaultValue:"20"
    }
    section("Kiểu báo động lần 1")
    {
        input name:"typ1",type:"enum", title:"Chọn kiểu báo động", options: ["A","L"], defaultValue:"L"
    }
    
    section ("Bao nhiêu giây thì kiểm tra lần 2, kể từ khi kết thúc lần 1")
    {
         input name: "tp", type: "number", title: "Thời gian(giây)?", defaultValue:"180"
    }
   
    section ("Thời gian báo động lần 2")
    {
         input name: "tp2", type: "number", title: "Báo động trong bao lâu(giây)?", defaultValue:"20"
    }
    
    section("Kiểu báo động lần 2")
    {
        input name:"typ2",type:"enum", title:"Chọn kiểu báo động", options: ["A","L"], defaultValue:"L"
    }
    
    section("Thiết bị báo động")
    {
	     input("alamH","capability.alarm",title:"Chọn thiết bị báo động")
    }
    
 }
 
def installed() 
{
    init()
}

def updated() 
{
	unschedule()
   	init()	
}

def init()
{
	subscribe(motionCD,"motion",motion_CD)
  	subscribe(alamH,"alarm",alam_H) 
}
def initialize() {
    // execute handlerMethod every hour on the half hour.
    //schedule("0 30 * * * ?", alamF)
}
def motion_CD(evt)
{
    def timeofB = timeToday(timeB)
    def timeofE= timeToday(timeE)
    def timeC= now()
    def p1= tp1*1000
    def p= tp*1000 
    def pp1=p+p1
    
    def dk1= (timeofB<timeofE) && (timeC >= timeofB.time && timeC<=timeofE.time)
    def dk2= (timeofB>timeofE) && (timeC >= timeofB.time || timeC<=timeofE.time)
    
    if (evt.value == "active" && sel == "on")
    {
    
        if (dk1 || dk2)
    		{ 
            	def t_m=motionCD
            	sendPush("Báo động bước 1, Kiểu ${typ1} ${evt.displayName} là ${evt.value} ")
               if(typ1=="L") { alamH.strobe()}
               if(typ1=="A") {alamH.siren()}
               if(typ1=="AL") { alamH.both()}  
              // schedule(now()+p1,alamF) // turn off in 10 second
               schedule(now()+pp1,laplai) // loop again in p second
    		}
    	}
}

def laplai()
{
	alamH.off()
    
    def timeofB = timeToday(timeB)
    def timeofE= timeToday(timeE)
    def timeC= now()
    def p2= tp2*1000  
    def t_m=motionCD.currentValue("motion")
    
    if (t_m == "active")
    {  
    	sendPush("Báo động bước 2, Kiểu ${typ2}")
        if(typ2=="L") {alamH.strobe()}
        if(typ2=="A") {alamH.siren()}
        if(typ2=="AL") {alamH.both()}  
        schedule(now() + p2,alamF)
    }
     
}

def alamF()
{
	alamH.off()
    
}

def alam_H(evt)
{
  if (evt.value == "strobe") 
  	{
    	sendPush("Báo động đang nhấp nháy đèn")
  	} 
  	if (evt.value == "siren") 
  	{
    	sendPush( "Báo động đang phát âm thanh")
  	}
  	if (evt.value == "both") 
  	{
    	sendPush ("Báo động đang phát đèn và âm thanh")
  	}
  	if (evt.value == "off") 
  	{
    	sendPush("Đã tắt báo động")
  	}
}