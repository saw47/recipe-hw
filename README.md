### Легенда
В рамках дипломного проекта вам необходимо разработать приложение, предоставляющее удобный доступ к кулинарной книге.

В приложении пользователи могут создавать свои собственные рецепты с описанием шагов приготовления, указанием категорий.

### Обязательный функционал:

* CRUD для списка рецептов + фильтры и поиск.
* Данные доступны при перезапуске приложения (предусмотреть хранение на диске).
* Минимум 2 элемента нижнего меню - рецепты и избранное. Пустого экрана при этом быть не должно. В случае отсутствия данных отображать заглушку.

### Необязательный функционал (по желанию):

* Валидация при выборе фильтров и создании/редактирования рецептов должна быть протестирована при помощи unit тестов (возможно, будет проще вынести эти функции в отдельный файл/класс). Необходимо добиться 100% покрытия.

//Если какой-то функциональности вам будет не хватать, можете обратиться к дипломному руководителю по вопросам её добавления.//


### Далее рассмотрим отдельно каждый элемент приложения.

#### Экран со списком рецептов

Каждый элемент списка содержит название, имя автора и категорию

Доступные категории:

* Европейская
* Азиатская
* Паназиатская
* Восточная
* Американская
* Русская
* Средиземноморская

По нажатию на элемент списка должен открываться экран детального просмотра рецепта. 

В правом верхнем углу должна быть кнопка вызова меню. 

В меню должны быть пункты удалить и изменить (см. пункт “Экран редактирования”). 

По долгому нажатию на элемент списка должен активироваться режим перемещения, чтобы поменять порядок рецептов при помощи drag and drop, либо вспомогательных кнопок. 

В правом нижнем углу добавить кнопку в виде тоггла, которая добавляет/удаляет рецепт в избранное.

В верхней, либо нижней части экрана должна располагаться строка поиска, которая фильтрует рецепты по названию. При этом, если строка пустая, то показываются все элементы.

Справа от строки поиска требуется поместить кнопку перехода к экрану фильтров.

#### Экран просмотра рецепта
Отображает название, автора, категорию и детальную информацию об этапах приготовления блюда в виде вертикального списка, либо карусели. 

Каждый этап может включать в себя картинку.

#### Экран редактирования
Позволяет изменить всю информацию о рецепте. 

Следует также предусмотреть возможность менять порядок шагов. Нельзя оставить поля пустыми, хотя бы один шаг должен быть в рецепте.

#### Экран создания рецепта
Аналогичен экрану редактирования, но при его открытии все поля не заполнены, а при сохранении происходит создание нового рецепта

#### Экран фильтров
Представляет из себя список чекбоксов/чипсов из категорий. 


какие 
