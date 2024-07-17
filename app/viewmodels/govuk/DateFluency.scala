/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package viewmodels.govuk

import play.api.data.Field
import play.api.i18n.Messages
import uk.gov.hmrc.govukfrontend.views.viewmodels.FormGroup
import uk.gov.hmrc.govukfrontend.views.viewmodels.content.Text
import uk.gov.hmrc.govukfrontend.views.viewmodels.dateinput.{DateInput, InputItem}
import uk.gov.hmrc.govukfrontend.views.viewmodels.fieldset.{Fieldset, Legend}
import uk.gov.hmrc.govukfrontend.views.viewmodels.hint.Hint
import uk.gov.hmrc.hmrcfrontend.views.Implicits.RichDateInput
import viewmodels.ErrorMessageAwareness

object date extends DateFluency

trait DateFluency {

  object DateViewModel extends ErrorMessageAwareness {

    def apply(
               field: Field,
               legend: Legend
             )(implicit messages: Messages): DateInput =
      apply(
        field    = field,
        fieldset = Fieldset(legend = Some(legend))
      )

  def apply(
             field: Field,
             fieldset: Fieldset
           )(implicit messages: Messages): DateInput = {

    // https://github.com/hmrc/play-frontend-hmrc?tab=readme-ov-file#richdateinput
    DateInput(
      hint = Some(Hint(content = Text("date.hint"))),
      fieldset = Some(fieldset)
    ).withDayMonthYearFormField(field)
  }
}

  implicit class FluentDate(date: DateInput) {

    def withNamePrefix(prefix: String): DateInput =
      date.copy(namePrefix = Some(prefix))

    def withHint(hint: Hint): DateInput =
      date.copy(hint = Some(hint))

    def withFormGroup(formGroup: FormGroup): DateInput =
      date.copy(formGroup = formGroup)

    def withCssClass(newClass: String): DateInput =
      date.copy(classes = s"${date.classes} $newClass")

    def withAttribute(attribute: (String, String)): DateInput =
      date.copy(attributes = date.attributes + attribute)

    def asDateOfBirth(): DateInput =
      date.copy(
        items = date.items map {
          item =>
            val name = item.id.split('.').last
            item.copy(autocomplete = Some(s"bday-$name"))
        })
  }
}
