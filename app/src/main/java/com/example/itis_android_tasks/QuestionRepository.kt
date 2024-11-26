package com.example.itis_android_tasks

object QuestionRepository {
    private val questions = mutableListOf(
        QuestionModel(
            id = 1,
            question = "Какой фрукт является символом нового года в Китае?",
            answers = listOf(
                "Апельсин",
                "Яблоко",
                "Грейпфрут",
                "Мандарин",
                "Лимон"
            )
        ),
        QuestionModel(
            id = 2,
            question = "Какой ингредиент является основным в итальянской пасте 'Карбонара'?",
            answers = listOf(
                "Бекон",
                "Курица",
                "Говядина",
                "Рыба",
                "Колбаса"
            )
        ),
        QuestionModel(
            id = 3,
            question = "Какой фрукт используется для приготовления мармелада?",
            answers = listOf(
                "Яблоко",
                "Груша",
                "Вишня",
                "Абрикос",
                "Слива"
            )
        ),
        QuestionModel(
            id = 4,
            question = "Какой напиток готовят из свежевыжатых апельсинов?",
            answers = listOf(
                "Апельсиновый сок",
                "Лимонад",
                "Морс",
                "Кола",
                "Чай"
            )
        ),
        QuestionModel(
            id = 5,
            question = "Какой овощ является основным ингредиентом в салате 'Цезарь'?",
            answers = listOf(
                "Салат Ромэн",
                "Огурец",
                "Помидор",
                "Лук",
                "Капуста"
            )
        ),
        QuestionModel(
            id = 6,
            question = "Какой продукт является основным ингредиентом в японской кухне?",
            answers = listOf(
                "Рис",
                "Хлеб",
                "Макароны",
                "Картофель",
                "Кукуруза"
            )
        ),
        QuestionModel(
            id = 7,
            question = "Какой фрукт является символом тропиков?",
            answers = listOf(
                "Пальма",
                "Банан",
                "Ананас",
                "Кокос",
                "Манго"
            )
        ),
        QuestionModel(
            id = 8,
            question = "Какой напиток готовят из свежевыжатых лимонов?",
            answers = listOf(
                "Лимонад",
                "Апельсиновый сок",
                "Морс",
                "Кола",
                "Молоко"
            )
        ),
        QuestionModel(
            id = 9,
            question = "Какой овощ является основным ингредиентом в салате 'Греческий'?",
            answers = listOf(
                "Огурец",
                "Помидор",
                "Лук",
                "Капуста",
                "Салат Ромэн"
            )
        ),
        QuestionModel(
            id = 10,
            question = "Какой продукт является основным ингредиентом в итальянской пасте 'Феттучини'?",
            answers = listOf(
                "Мука",
                "Яйцо",
                "Молоко",
                "Соль",
                "Сахар"
            )
        )
    )

    fun getQuestions(): List<QuestionModel> {
        return questions
    }

    fun getQuestionById(id: Int): QuestionModel? = questions.find { it.id == id }

    fun getSize(): Int = questions.size
}