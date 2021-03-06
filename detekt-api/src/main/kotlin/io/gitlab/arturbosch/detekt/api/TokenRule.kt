package io.gitlab.arturbosch.detekt.api

import org.jetbrains.kotlin.com.intellij.lang.ASTNode
import org.jetbrains.kotlin.com.intellij.psi.PsiWhiteSpace
import org.jetbrains.kotlin.com.intellij.psi.impl.source.tree.LeafPsiElement
import org.jetbrains.kotlin.psi.KtFile

/**
 * A token rule overrides the visiting process and prohibits the use of standard visit functions.
 * As every ASTNode is considered as single object, this abstract rule can be used to define
 * rules which operate on spaces, tabs, semicolons etc.
 *
 * Token rules must define a procedure function which specifies what to do with each ASTNode.
 *
 * @author Artur Bosch
 */
@Suppress("EmptyFunctionBlock")
abstract class TokenRule(config: Config = Config.empty) : Rule(config) {

	override fun visit(root: KtFile) {
		ifRuleActive {
			preVisit(root)
			root.node.visitTokens { procedure(it) }
			postVisit(root)
		}
	}

	/**
	 * Every ASTNode is considered in isolation. Use 'is' operator to search for wished elements.
	 */
	open fun procedure(node: ASTNode) {
		when (node) {
			is PsiWhiteSpace -> visitSpaces(node)
			is LeafPsiElement -> visitLeaf(node)

		}
	}

	open fun visitLeaf(leaf: LeafPsiElement) {
		if (!leaf.isPartOfString()) {
			when (leaf.text) {
				"}" -> visitRightBrace(leaf)
				"{" -> visitLeftBrace(leaf)
				":" -> visitColon(leaf)
				";" -> visitSemicolon(leaf)
				";;" -> visitDoubleSemicolon(leaf)
				"," -> visitComma(leaf)
			}
		}
	}

	protected open fun visitSemicolon(leaf: LeafPsiElement) {
	}

	protected open fun visitDoubleSemicolon(leaf: LeafPsiElement) {
	}

	protected open fun visitComma(leaf: LeafPsiElement) {
	}

	protected open fun visitColon(colon: LeafPsiElement) {
	}

	protected open fun visitLeftBrace(brace: LeafPsiElement) {
	}

	protected open fun visitRightBrace(brace: LeafPsiElement) {
	}

	protected open fun visitSpaces(space: PsiWhiteSpace) {
	}

}
