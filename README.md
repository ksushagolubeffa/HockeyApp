# HockeyApp
комментарии к работе:
1) из-за того, что не было бесплатных апишек, где лого команды приходило бы как значения, я использовала дефолтные эмблемы (при желании вытащить еще один параметр труда не составит)
2) в апи, которую нашла я (открытую на гитхабе), нету матчей ближайшие несколько месяцев (поэтому инфа там со статической датой, которую я там захардкодила, но при этом закоменчен корректный вариант). соответственно на экране, где можно увидеть текущие игры, они уже завершены. но при этом в коде я прописала логику для текущих игр: каждую минуту будет происходит обновление данных (подгружаться голы, удары по воротам и меняться минуты соответственно, это можно посмотреть в GameFragment)
3) за монетки у меня можно получить доступ к детальной информации по игре (в каком периоде какая команда сколько забила и тп).

## Какие технологии использованы:
- MVVM, Clean Architecture
- Dagger 2
- Retrofit, OkHttp, GsonConverter
- XML верстка
- Jetpack Navigation (Navigator, Router, BottomNavigationView)
