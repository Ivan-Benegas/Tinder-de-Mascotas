GitHub Markup
This library is the first step of a journey that every markup file in a repository goes on before it is rendered on GitHub.com:

github-markup selects an underlying library to convert the raw markup to HTML. See the list of supported markup formats below.
The HTML is sanitized, aggressively removing things that could harm you and your kin—such as script tags, inline-styles, and class or id attributes.
Syntax highlighting is performed on code blocks. See github/linguist for more information about syntax highlighting.
The HTML is passed through other filters that add special sauce, such as emoji, task lists, named anchors, CDN caching for images, and autolinking.
The resulting HTML is rendered on GitHub.com.
Please note that only the first step is covered by this gem — the rest happens on GitHub.com. In particular, markup itself does no sanitization of the resulting HTML, as it expects that to be covered by whatever pipeline is consuming the HTML.

Please see our contributing guidelines before reporting an issue.

Markups
The following markups are supported. The dependencies listed are required if you wish to run the library. You can also run script/bootstrap to fetch them all.

.markdown, .mdown, .mkdn, .md -- gem install commonmarker (https://github.com/gjtorikian/commonmarker)
.textile -- gem install RedCloth (https://github.com/jgarber/redcloth)
.rdoc -- gem install rdoc -v 3.6.1
.org -- gem install org-ruby (https://github.com/wallyqs/org-ruby)
.creole -- gem install creole (https://github.com/larsch/creole)
.mediawiki, .wiki -- gem install wikicloth (https://github.com/nricciar/wikicloth)
.rst -- pip install docutils
.asciidoc, .adoc, .asc -- gem install asciidoctor (http://asciidoctor.org)
.pod -- Pod::Simple::XHTML comes with Perl >= 5.10. Lower versions should install Pod::Simple from CPAN.
Installation
gem install github-markup
or

bundle install
from this directory.

Usage
Basic form:

require 'github/markup'

GitHub::Markup.render('README.markdown', "* One\n* Two")
More realistic form:

require 'github/markup'

GitHub::Markup.render(file, File.read(file))
And a convenience form:

require 'github/markup'

GitHub::Markup.render_s(GitHub::Markups::MARKUP_MARKDOWN, "* One\n* Two")
Contributing
See Contributing.
