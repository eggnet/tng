package ast;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.PackageDeclaration;

public class Visitor extends ASTVisitor {
	
	public Visitor() {
		
	}
	
	/**
	 * This function overrides what to do when the AST visitor 
	 * encounters a package declaration
	 */
	@Override
	public boolean visit(PackageDeclaration node) {
		System.out.println("Package declaration");
		
		return super.visit(node);
	}
	
	/**
	 * This function overrides what to do when we reach
	 * a method declaration inside a class.
	 */
	@Override
	public boolean visit(MethodDeclaration node) {
		System.out.println("Method declaration");
		
		return super.visit(node);
	}
	
	/**
	 * This function overrides what to do when we reach
	 * a method invocation statement.
	 */
	@Override
	public boolean visit(MethodInvocation node) {
		System.out.println("Method invocation: " + node.toString());
		
		IMethodBinding binding = node.resolveMethodBinding();
		
		if(binding != null) {
			System.out.println(binding.toString());
			System.out.println("Declaring class: " + binding.getDeclaringClass().getQualifiedName());
		}
		
		return super.visit(node);
	}
}
