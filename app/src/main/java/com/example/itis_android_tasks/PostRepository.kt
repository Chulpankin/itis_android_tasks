package com.example.itis_android_tasks


import kotlin.random.Random

object PostRepository {

    private val posts = mutableListOf(
        PostModel(
            id = 1,
            title = "Cat",
            description = "Кошка — домашнее животное, " +
                    "млекопитающее семейства кошачьих отряда хищных. " +
                    "Одиночный охотник на грызунов и других мелких животных. " +
                    "Для общения использует звуковые сигналы, феромоны и движения тела.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=800997bd886c3431cb92d8191e651e23_l-4559382-images-thumbs&n=13"
        ),
        PostModel(
            id = 2,
            title = "Dog",
            description = "Собаки - полезные домашние питомцы," +
                    " отличающиеся ярко выраженным социальным поведением " +
                    "и удивительными способностями к обучению.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=e2ce1f2006ef61b3b7f1e49ad3c0283f20516170-13098462-images-thumbs&n=13"
        ),
        PostModel(
            id = 3,
            title = "Racoon",
            description = "Еноты — род хищных млекопитающих семейства енотовых. " +
                    "Естественный ареал обитания — Северная и Центральная Америка. " +
                    "В Евразии и на территории Российской Федерации представлен " +
                    "единственный вид — енот-полоскун.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=4a848f4a8c823acf0c37bce0a9112cd3eda7d96a-10878209-images-thumbs&n=13"
        ),
        PostModel(
            id = 4,
            title = "Rabbit",
            description = "Кролики — общее название нескольких родов млекопитающих " +
                    "из семейства зайцевых (в том числе и домашних кроликов)." +
                    "Дикий кролик имеет длину тела 31–45 см, хвоста — 5–6 см, " +
                    "ушей — до 7 см, массу 1–2 кг. Мех мягкий, густой, " +
                    "на спине светло-серый или рыжеватый, на брюхе несколько светлее.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=abe9c377612cc485e8280aa9b564b375de506271-5283999-images-thumbs&n=13"
        ),
        PostModel(
            id = 5,
            title = "Elephant",
            description = "Слоны — самые крупные наземные млекопитающие. " +
                    "Они обладают отличной памятью и социальной структурой. " +
                    "Слоны используют инфразвуковые волны для коммуникации на больших расстояниях.",
            imageUrl = "https://cdn.culture.ru/images/441d2b29-18e5-56ac-bc5c-3d8f3b6f26bd"
        ),
        PostModel(
            id = 6,
            title = "Tiger",
            description = "Тигры — одни из самых крупных и сильных кошачьих. " +
                    "Они обитают в основном в Азии. Тигры — одиночные охотники, " +
                    "которые охотятся на крупных млекопитающих.",
            imageUrl = "https://www.bmwclub.ru/proxy.php?image=https%3A%2F%2Fmasyamba.ru%2F%25D0%25BA%25D0%25B0%25D1%2580%25D1%2582%25D0%25B8%25D0%25BD%25D0%25BA%25D0%25B8-%25D1%2582%25D0%25B8%25D0%25B3%25D1%2580%25D0%25B0%2F59-%25D0%25BA%25D0%25B0%25D1%2580%25D1%2582%25D0%25B8%25D0%25BD%25D0%25BA%25D0%25B8-%25D1%2582%25D0%25B8%25D0%25B3%25D1%2580%25D1%258B-%25D0%25BA%25D1%2580%25D0%25B0%25D1%2581%25D0%25B8%25D0%25B2%25D1%258B%25D0%25B5.jpg&hash=27bccfd06260a84ed81832ad3f3b4add"
        ),
        PostModel(
            id = 7,
            title = "Giraffe",
            description = "Жирафы — самые высокие млекопитающие на планете. " +
                    "Они обитают в саваннах Африки. Жирафы имеют длинные шеи, " +
                    "которые используют для доступа к листьям на высоких деревьях.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=9db3af90e8af477f0a5ba892b16e9189296ef6ba-12629451-images-thumbs&n=13"
        ),
        PostModel(
            id = 8,
            title = "Penguin",
            description = "Пингвины — нелетающие птицы, обитающие в основном в Антарктике. " +
                    "Они отлично приспособлены к холодному климату и умеют плавать. " +
                    "Пингвины живут в колониях и заботятся о своих птенцах.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=e0fcd45812bc52a33839435f87bd334ba43c1591-4767909-images-thumbs&n=13"
        ),
        PostModel(
            id = 9,
            title = "Alpaka",
            description = "Альпака — домашнее мозоленогое животное, " +
                    "предположительно произошедшее от викуньи." +
                    "Рост альпак не превышает одного метра в холке, " +
                    "их масса около 70 килограммов. Они обладают мягким и длинным руном.",
            imageUrl = "https://avatars.mds.yandex.net/get-entity_search/1993342/975129710/SUx182"
        ),
        PostModel(
            id = 10,
            title = "Rat",
            description = "Крыса считается одним из наиболее распространенных животных на планете, " +
                    "а ископаемые останки самых первых крыс пролежали в земле несколько миллионов лет.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=66ba30e860c46ac4cd9ba13f44c644fedba5dac4-9858449-images-thumbs&n=13"
        ),
        PostModel(
            id = 11,
            title = "Capybara",
            description = "Капибара — это полуводный травоядный грызун с квадратной " +
                    "мордочкой, короткими ногами, маленькими ушками и крохотным, " +
                    "почти незаметным хвостиком.",
            imageUrl = "https://yastatic.net/naydex/yandex-search/UzGaF8733/4dd91e5SR/kMC7t5W3CvRnPZDwbG0MmAkHJ1N5_YWJfPFNqP_vVgECG-oJGyJa-ayO8-A_02bzjjtbClgf2Zu1GytDbMKg9Y34toKkriKVEmiYjTxNAIwBclJfPYztmAiYRNme-3I0ZgfnvfO22ygBb3YV-i6H3tNaHn9pK4CPRfQ"
        ),
        PostModel(
            id = 12,
            title = "Hamster",
            description = "Хомяки — подсемейство грызунов семейства хомяковых. " +
                    "Включает 19 видов, относящихся к семи родам. " +
                    "Стали популярными домашними питомцами.",
            imageUrl = "https://yastatic.net/naydex/yandex-search/UzGaF6549/4dd91e5SR/kMC7t5W3CvRnPZDwbG0MmAkHJ1N5_YWwFIRoyZLqOl13vudVC3pTgcCy_-w_717j6gNfLkVOmZbsRgbDSbf5zUBl94dtA8NI29FhAKz1kDB5Yid7ec2B5FQpo"
        ),
        PostModel(
            id = 13,
            title = "Cavy",
            description = "Морская свинка — вид одомашненных грызунов из рода свинок семейства свинковых. Несмотря на название, не связаны с семейством свиней и не являются морскими животными.",
            imageUrl = "https://yastatic.net/naydex/yandex-search/UzGaF6445/4dd91e5SR/kMC7t5W3CvRnPZDwbG0MmAkHJ1N5_YWwFIRoyZLqOl13vudVC3pTgcCa8-gvz1rf6idPLk12gYrwVmMywALI6PXQQxsVe88w7-V5VKzRUIwldgsKVeTtUPw"
        ),
        PostModel(
            id = 14,
            title = "Bober",
            description = "Бобр — обладатель красивого меха, " +
                    "окраска которого варьирует у разных животных " +
                    "от светло-коричневого до почти черного.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=8f586455b8ce058f7c54decfb40cab4c5075d0ad-5177977-images-thumbs&n=13"
        ),
        PostModel(
            id = 15,
            title = "Whale",
            description = "Морское млекопитающее из инфраотряда китообразных, " +
                    "относящееся к семейству полосатиковых. Это самый крупный кит, " +
                    "а также, вероятно, самое тяжёлое из всех животных, " +
                    "когда-либо существовавших на планете.",
            imageUrl = "https://avatars.mds.yandex.net/i?id=112f89519227287c433818e755c09dc8e3f13886-11908978-images-thumbs&n=13"
        ),
        PostModel(
            id = 16,
            title = "Monkey",
            description = "Обезьяны — группа млекопитающих из отряда приматов. " +
                    "В биологической систематике название «обезьяны» может применяться " +
                    "по отношению ко всем представителям инфраотряда Simiiformes",
            imageUrl = "https://yastatic.net/naydex/yandex-search/UzGaF8785/4dd91e5SR/kMC7t5W3CvRnPZDwbG0MmAkHJ1N5_YWJfPFNqO6_UhROH_YFAzcG4PHe2rVzx2-3gjtbNwAbwMO8Tl9TRMK4_PSV8-KkugaBCkiYlU1dELwZegsmWfyZ-DTdEcWujl9hKjfuDfPO2yR1LwJ5whabArNmNnsccvCT7ffE"
        ),
        PostModel(
            id = 17,
            title = "Axolotl",
            description = "Неотеническая личинка некоторых видов амбистом, земноводных " +
                    "из семейства амбистомовых отряда хвостатых.",
            imageUrl = "https://yastatic.net/naydex/yandex-search/UzGaF6549/4dd91e5SR/kMC7t5W3CvRnPZDwbG0MmAkHJ1N5_YWwFIRoyZLqOl13vudVC3pTgcCW2-g_y37n6gNDJk1GqYbcWgbDVZfpzUBl94dtA8NI29FhAKz1kDB5Yid7ec2B5FQpo"
        ),
        PostModel(
            id = 18,
            title = "Megalodon",
            description = "Мегалодо́н — вид вымерших акул из семейства Otodontidae. " +
                    "Существовал в миоцене и плиоцене, хотя есть некоторые спорные данные " +
                    "о более древних и о более новых находках.",
            imageUrl = "https://yastatic.net/naydex/yandex-search/UzGaF6497/4dd91e5SR/kMC7t5W3CvRnPZDwbG0MmAkHJ1N5_YWwFIRoyZLqOl13vudVC3pTgcCW-9Ajz26HsgNrLllyqZbgN_babZPI5WnQ3xtte8NEr5EVbJTNjOwZXg9_DJTx-Pwo"
        ),
        PostModel(
            id = 19,
            title = "Boar",
            description = "Кабан (Sus scrofa) — млекопитающее семейства свиных отряда парнокопытных." +
                    " Длина тела кабана может достигать 200 см, высота в холке — 100 см, " +
                    "масса — до 300 кг.",
            imageUrl = "https://avatars.mds.yandex.net/get-entity_search/143021/953638145/S600xU_2x"
        ),
        PostModel(
            id = 20,
            title = "Hedgehog",
            description = "Обыкновенный ёж — вид млекопитающих из " +
                    "рода евразийских ежей семейства ежовых. Обитает в широколиственных лесах " +
                    "Западной и Центральной Европы.",
            imageUrl = "https://avatars.mds.yandex.net/get-entity_search/10844899/953732982/S600xU_2x"
        ),
        PostModel(
            id = 21,
            title = "Crocodile",
            description = "Крокодилы имеют крупное, плотное, ящероподобное тело, " +
                    "вытянутую и приплюснутую морду, сжатые с боков хвосты и глаза, " +
                    "уши и ноздри, располагающиеся на верхней части головы. ",
            imageUrl = "https://avatars.mds.yandex.net/get-entity_search/2321801/777958457/SUx182"
        ),
        PostModel(
            id = 22,
            title = "Caracal",
            description = "Кошка средних размеров, которая выглядит внушительно на контрасте с " +
                    "обычными домашними питомцами. Это высокие и стройные хищники.",
            imageUrl = "https://avatars.mds.yandex.net/get-entity_search/6246269/952278956/S184xU_2x"
        ),
        PostModel(
            id = 23,
            title = "Serval",
            description = "Сервал, или кустарниковая кошка, — хищное млекопитающее семейства кошачьих. " +
                    "Это стройная, длинноногая кошка средних размеров. ",
            imageUrl = "https://avatars.mds.yandex.net/i?id=a10dac9e28de4e8546db7df9aabf6f8d2ad634e1-12718238-images-thumbs&n=13"
        ),
        PostModel(
            id = 24,
            title = "Nutria",
            description = "Млекопитающее отряда грызунов. Внешне нутрия напоминает большую крысу. " +
                    "Длина её тела составляет до 60 см, хвоста — до 45 см, вес — 5–12 кг. ",
            imageUrl = "https://avatars.mds.yandex.net/get-entity_search/2334190/934783247/S184xU_2x"
        ),
        PostModel(
            id = 25,
            title = "Colibri",
            description = "Самые мелкие птицы на нашей планете. Их вес разнится от вида к виду и " +
                    "может быть 1,5–2 грамма или достигать 20–25 граммов.",
            imageUrl = "https://avatars.mds.yandex.net/get-entity_search/1546299/1007827911/S184xU_2x"
        )
    )

    private val titles = listOf(
        "Lion", "Bear", "Wolf", "Fox", "Deer", "Horse", "Cow", "Sheep", "Manul", "Frog"
    )

    private val descriptions = listOf(
        "Лев — крупный хищник, обитающий в Африке.",
        "Медведь — крупное млекопитающее, обитающее в различных регионах.",
        "Волк — хищник, обитающий в лесах и степях.",
        "Лиса — хищник, обитающий в лесах и степях.",
        "Олень — парнокопытное млекопитающее, обитающее в лесах и степях.",
        "Лошадь — домашнее животное, используемое для верховой езды.",
        "Корова — домашнее животное, используемое для получения молока.",
        "Овца — домашнее животное, используемое для получения шерсти.",
        "Манул — хищное млекопитающее семейства кошачьих.",
        "Лягушка — общеупотребительное название группы животных из отряда бесхвостых земноводных. "
    )

    private val imageUrls = listOf(
        "https://yastatic.net/naydex/yandex-search/UzGaF8785/4dd91e5SR/kMC7t5W3CvRnPZDwbG0MmAkHJ1N5_YWJfPFNqb_fRhhCI_NZAmZG4ZnG2_Frx3uzs2YLMwAWrbr0RnYCHMfttMiV2r6kugadAki8lV1dELwZegsmWfyZ-DTdEcWujl9hKjfuDfPO2yR1LwJ5whabArNmNnsccvCT7ffE",
        "https://yastatic.net/naydex/yandex-search/UzGaF8733/4dd91e5SR/kMC7t5W3CvRnPZDwbG0MmAkHJ1N5_YWJfPFNqb6yGhxXU_tUbyZa7aCPq-gGkju_j3daflAanNe8UnoCHMPJuMnAt_KkqgqdBmy4iTxNAIwBclJfPYztmAiYRNme-3I0ZgfnvfO22ygBb3YV-i6H3tNaHn9pK4CPRfQ",
        "https://yastatic.net/naydex/yandex-search/UzGaF8733/4dd91e5SR/kMC7t5W3CvRnPZDwbG0MmAkHJ1N5_YWJfPFNqPfbShUGE_ocUzsC5aSy6-F333uvniNbKlVGjbrxGnIaAYvo4N38rrakrhatNnCMrTxNAIwBclJfPYztmAiYRNme-3I0ZgfnvfO22ygBb3YV-i6H3tNaHn9pK4CPRfQ",
        "https://yastatic.net/naydex/yandex-search/UzGaF8733/4dd91e5SR/kMC7t5W3CvRnPZDwbG0MmAkHJ1N5_YWJfPFNqP_-FgBCGq9QRycbraSy6qlqg3bix2dWekV3zY71Dn4WBYP8zNyR8qKkqiaRDmichTxNAIwBclJfPYztmAiYRNme-3I0ZgfnvfO22ygBb3YV-i6H3tNaHn9pK4CPRfQ",
        "https://yastatic.net/naydex/yandex-search/UzGaF8733/4dd91e5SR/kMC7t5W3CvRnPZDwbG0MmAkHJ1N5_YWJfPFNqOPaD0ULSqIQbyMO7bCe8-Qigie_hitWcx1GiMrsSy4WCMPI5PXUqoKkqg6RNki4mTxNAIwBclJfPYztmAiYRNme-3I0ZgfnvfO22ygBb3YV-i6H3tNaHn9pK4CPRfQ",
        "https://avatars.mds.yandex.net/i?id=4ec05244fda6432d002c9f04a163f66c7aa73a5b-3985701-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=59a808bc2bb33a4c0ccf0c4e274e17ad4d6de069-4406391-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=6cca1ffbab8ae482bfa6cb299e0544b79ffeaee0-2710895-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=e753b684bbdecc668cdb6ab0685ebdb62b852803-9066051-images-thumbs&n=13",
        "https://avatars.mds.yandex.net/i?id=f9358112f556356160f672aed89a1c8b20fe0f69-8309375-images-thumbs&n=13"
    )

    fun getPosts(): List<PostModel> = posts

    fun getPostById(id: Long): PostModel? =
        posts.find { it.id == id }


    fun addRandomItems(count: Int) {
        repeat(count) {
            addOneRandomItem()
        }
    }

    fun removeRandomItems(count: Int) {
        repeat(minOf(count, posts.size)) {
            removeOneRandomItem()
        }
    }

    fun addOneRandomItem() {
        val randomIndex = Random.nextInt(0, posts.size)
        posts.add(randomIndex, generateRandomPostModel())
    }

    fun removeOneRandomItem() {
        if (posts.isNotEmpty()) {
            val randomIndex = Random.nextInt(0, posts.size)
            posts.removeAt(randomIndex)
        }
    }

    private fun generateRandomPostModel(): PostModel {
        val newId = posts.maxOf { it.id } + 1
        return PostModel(
            id = newId,
            title = titles.random(),
            description = descriptions.random(),
            imageUrl = imageUrls.random()
        )
    }

    fun removeItem(position: Int) {
        posts.removeAt(position)
    }
}