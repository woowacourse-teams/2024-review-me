package reviewme.template.domain.structured;

import reviewme.template.domain.Template;

public class StructuredTemplate {

    private final Template template;
    private final Sections sections;
    private final Questions questions;
    private final OptionGroups optionGroups;
    private final OptionItems optionItems;

    StructuredTemplate(Template template, Sections sections, Questions questions, OptionGroups optionGroups,
                              OptionItems optionItems) {
        this.template = template;
        this.sections = sections;
        this.questions = questions;
        this.optionGroups = optionGroups;
        this.optionItems = optionItems;
    }

    StructuredTemplate(Template template, Sections sections, Questions questions) {
        this.template = template;
        this.sections = sections;
        this.questions = questions;
        this.optionGroups = null;
        this.optionItems = null;
    }

    public long getTemplateId() {
        return template.getId();
    }
}
