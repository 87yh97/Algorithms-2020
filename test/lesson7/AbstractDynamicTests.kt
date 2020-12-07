package lesson7

import kotlin.test.assertEquals

abstract class AbstractDynamicTests {
    fun longestCommonSubSequence(longestCommonSubSequence: (String, String) -> String) {
        assertEquals("", longestCommonSubSequence("мой мир", "я"))
        assertEquals("1", longestCommonSubSequence("1", "1"))
        assertEquals("13", longestCommonSubSequence("123", "13"))
        assertEquals("здс", longestCommonSubSequence("здравствуй мир", "мы здесь"))
        assertEquals("emt ole", longestCommonSubSequence("nematode knowledge", "empty bottle"))
        val expectedLength = "e kerwelkkd r".length
        assertEquals(
            expectedLength, longestCommonSubSequence(
                "oiweijgw kejrhwejelkrw kjhdkfjs hrk",
                "perhkhk lerkerorwetp lkjklvvd durltr"
            ).length, "Answer must have length of $expectedLength, e.g. 'e kerwelkkd r' or 'erhlkrw kjk r'"
        )
        val expectedLength2 = """ дд саы чтых,
евшнео ваа се сви дн.
        """.trimIndent().length
        assertEquals(
            expectedLength2, longestCommonSubSequence(
                """
Мой дядя самых честных правил,
Когда не в шутку занемог,
Он уважать себя заставил
И лучше выдумать не мог.
                """.trimIndent(),
                """
Так думал молодой повеса,
Летя в пыли на почтовых,
Всевышней волею Зевеса
Наследник всех своих родных.
                """.trimIndent()
            ).length, "Answer must have length of $expectedLength2"
        )
        assertEquals("", longestCommonSubSequence("", "")) //Custom
        assertEquals( //Custom
            "2 3 5 7 11 13 17 19 23 29\n" +
                    "\n" +
                    "31 37 41 43 47 53 59 61 67 71\n" +
                    "\n" +
                    "73 79 83 89 97 101 103 107 109 113\n" +
                    "\n" +
                    "127 131 137 139 149 151 157 163 167 173\n" +
                    "\n" +
                    "179 181 191 193 197 199 211 223 227 229\n" +
                    "\n" +
                    "233 239 241 251 257 263 269 271 277 281\n" +
                    "\n" +
                    "283 293 307 311 313 317 331 337 347 349\n" +
                    "\n" +
                    "353 359 367 373 379 383 389 397 401 409\n" +
                    "\n" +
                    "419 421 431 433 439 443 449 457 461 463\n" +
                    "\n" +
                    "467 479 487 491 499 503 509 521 523 541\n" +
                    "\n" +
                    "547 557 563 569 571 577 587 593 599 601\n" +
                    "\n" +
                    "607 613 617 619 631 641 643 647 653 659\n" +
                    "\n" +
                    "661 673 677 683 691 701 709 719 727 733\n" +
                    "\n" +
                    "739 743 751 757 761 769 773 787 797 809\n" +
                    "\n" +
                    "811 821 823 827 829 839 853 857 859 863\n" +
                    "\n" +
                    "877 881 883 887 907 911 919 929 937 94 ", longestCommonSubSequence(
                "1 2 3 5 7 11 13 17 19 23 29\n" +
                        "\n" +
                        "31 37 41 43 47 53 59 61 67 71\n" +
                        "\n" +
                        "73 79 83 89 97 101 103 107 109 113\n" +
                        "\n" +
                        "127 131 137 139 149 151 157 163 167 173\n" +
                        "\n" +
                        "179 181 191 193 197 199 211 223 227 229\n" +
                        "\n" +
                        "233 239 241 251 257 263 269 271 277 281\n" +
                        "\n" +
                        "283 293 307 311 313 317 331 337 347 349\n" +
                        "\n" +
                        "353 359 367 373 379 383 389 397 401 409\n" +
                        "\n" +
                        "419 421 431 433 439 443 449 457 461 463\n" +
                        "\n" +
                        "467 479 487 491 499 503 509 521 523 541\n" +
                        "\n" +
                        "547 557 563 569 571 577 587 593 599 601\n" +
                        "\n" +
                        "607 613 617 619 631 641 643 647 653 659\n" +
                        "\n" +
                        "661 673 677 683 691 701 709 719 727 733\n" +
                        "\n" +
                        "739 743 751 757 761 769 773 787 797 809\n" +
                        "\n" +
                        "811 821 823 827 829 839 853 857 859 863\n" +
                        "\n" +
                        "877 881 883 887 907 911 919 929 937 941 ", "2 3 5 7 11 13 17 19 23 29\n" +
                        "\n" +
                        "31 37 41 43 47 53 59 61 67 71\n" +
                        "\n" +
                        "73 79 83 89 97 101 103 107 109 113\n" +
                        "\n" +
                        "127 131 137 139 149 151 157 163 167 173\n" +
                        "\n" +
                        "179 181 191 193 197 199 211 223 227 229\n" +
                        "\n" +
                        "233 239 241 251 257 263 269 271 277 281\n" +
                        "\n" +
                        "283 293 307 311 313 317 331 337 347 349\n" +
                        "\n" +
                        "353 359 367 373 379 383 389 397 401 409\n" +
                        "\n" +
                        "419 421 431 433 439 443 449 457 461 463\n" +
                        "\n" +
                        "467 479 487 491 499 503 509 521 523 541\n" +
                        "\n" +
                        "547 557 563 569 571 577 587 593 599 601\n" +
                        "\n" +
                        "607 613 617 619 631 641 643 647 653 659\n" +
                        "\n" +
                        "661 673 677 683 691 701 709 719 727 733\n" +
                        "\n" +
                        "739 743 751 757 761 769 773 787 797 809\n" +
                        "\n" +
                        "811 821 823 827 829 839 853 857 859 863\n" +
                        "\n" +
                        "877 881 883 887 907 911 919 929 937 94 "
            )
        )
    }

    fun longestIncreasingSubSequence(longestIncreasingSubSequence: (List<Int>) -> List<Int>) {
        assertEquals(listOf(), longestIncreasingSubSequence(listOf()))
        assertEquals(listOf(1), longestIncreasingSubSequence(listOf(1)))
        assertEquals(listOf(1, 2), longestIncreasingSubSequence(listOf(1, 2)))
        assertEquals(listOf(2), longestIncreasingSubSequence(listOf(2, 1)))
        assertEquals(
            listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10),
            longestIncreasingSubSequence(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
        )
        assertEquals(listOf(2, 8, 9, 12), longestIncreasingSubSequence(listOf(2, 8, 5, 9, 12, 6)))
        assertEquals(
            listOf(23, 34, 56, 87, 91, 98, 140, 349), longestIncreasingSubSequence(
                listOf(
                    23, 76, 34, 93, 123, 21, 56, 87, 91, 12, 45, 98, 140, 12, 5, 38, 349, 65, 94,
                    45, 76, 15, 99, 100, 88, 84, 35, 88
                )
            )
        )
    }

    fun shortestPathOnField(shortestPathOnField: (String) -> Int) {
        assertEquals(1, shortestPathOnField("input/field_in2.txt"))
        assertEquals(12, shortestPathOnField("input/field_in1.txt"))
        assertEquals(43, shortestPathOnField("input/field_in3.txt"))
        assertEquals(28, shortestPathOnField("input/field_in4.txt"))
        assertEquals(222, shortestPathOnField("input/field_in5.txt"))
        assertEquals(15, shortestPathOnField("input/field_in6.txt"))
    }

}