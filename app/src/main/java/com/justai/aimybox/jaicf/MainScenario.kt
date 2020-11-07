package com.justai.aimybox.jaicf

import com.justai.jaicf.activator.caila.caila
import com.justai.jaicf.activator.dialogflow.dialogflow
import com.justai.jaicf.activator.rasa.rasa
import com.justai.jaicf.channel.aimybox.aimybox
import com.justai.jaicf.hook.AfterProcessHook
import com.justai.jaicf.model.scenario.Scenario

object MainScenario: Scenario() {

    init {
        /**
         * Appends these buttons for every response
         */

        state("hello") { // сюда переходим после первого будильника
            activators {
                intent("hello")
            }

            action {
                reactions.aimybox?.audio("hello.mp3")
            }
        }

        state("hello_again") {// сюда после второго
            activators {
                intent("hello")
            }

            action {
                reactions.aimybox?.audio("hello_again.mp3")
                reactions.aimybox?.audio("task.mp3")
            }
            state("dont_want_prisedaniya") {// он не хочет приседать, даем ему отжимания и спрашиваем готов ли он
                activators {
                    intent("no_prised")
                }
                action {
                    reactions.aimybox?.audio("pathetic.mp3")
                    reactions.aimybox?.audio("ready")
                }
                state("ready") {// если готов
                    activators {
                        intent("yes ready")
                    }
                    action {
                        reactions.aimybox?.audio("start.mp3")
                    }
                    state("pushups_end") {// когда он говорит что все спрашиваем сколько
                        activators {
                            intent("pushups_end")
                        }
                        action {
                            reactions.aimybox?.audio("how_much")
                        }
                        state ("check_timing") {// проверяем как там по таймингу
                            activators {
                                intent("number")
                            }
                            action {
                                reactions.aimybox?.audio("syke") // если тайминг не совпадает
                                reactions.go("ready")
                            }
                        }
                        state("name_amount") {//если говорит не цифру просим повторить
                            activators {
                                intent("fallback")
                            }
                            action {
                                reactions.aimybox?.audio("fallback")
                                reactions.go("check_timing")
                            }
                        }
                        state("planka") {//TODO: логику для планки в тч логику подбадривания и как сюда переходим
                        }
                    }
                    }
                }
            }



        fallback {
            reactions.sayRandom(
                "Sorry, I didn't get that.",
                "Sorry, could you repeat please?"
            )
        }
    }
}