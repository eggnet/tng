package ast;

import java.util.Stack;

import models.Method;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

import db.DatabaseConnector;

public class Visitor extends ASTVisitor {
	
	private Stack<Integer> methodStack;
	private String file;
	CompilationUnit cu;
	DatabaseConnector db;
	
	public Visitor(String file, CompilationUnit cu, DatabaseConnector db) {
		methodStack = new Stack<Integer>();
		this.file = file;
		this.db = db;
		this.cu = cu;
	}
	
	/**
	 * This function overrides what to do when we reach
	 * a method declaration.
	 */
	@Override
	public boolean visit(MethodDeclaration node) {
		// Insert the method into the DB
		IMethodBinding methodBinding = node.resolveBinding();
		Method method = createMethodFromBinding(node, methodBinding);
		
		// Insert
		int id = db.upsertMethod(method);
		
		// Push onto stack
		if(id != -1)
			methodStack.push(id);
		
		return super.visit(node);
	}
	
	/**
	 * This function overrides what to do when we reach
	 * the end of a method declaration.
	 */
	@Override
	public void endVisit(MethodDeclaration node) {
		methodStack.pop();
	}
	
	/**
	 * This function overrides what to do when we reach
	 * a method invocation statement.
	 */
	@Override
	public boolean visit(MethodInvocation node) {
		// Insert the method into the DB
		IMethodBinding methodBinding = node.resolveMethodBinding();
		Method method = createMethodFromBinding(null, methodBinding);
		
		// Add method call
		
		return super.visit(node);
	}
	
	private Method createMethodFromBinding(MethodDeclaration node, IMethodBinding methodBinding) {
		if(methodBinding != null) {
			Method method = new Method();
			method.setName(methodBinding.getName());
			method.setFile(file);
			if(node != null) {
				method.setStart(cu.getLineNumber(node.getStartPosition()));
				method.setEnd(cu.getLineNumber(node.getStartPosition() + node.getLength()));
			}
			
			// Parameters
			/*ITypeBinding[] parameters = methodBinding.getParameterTypes();
			if(parameters.length > 0) {
				for(int i = 0; i < parameters.length; i++) {
					if(parameters[i] != null)
						method.addParameter(parameters[i].getQualifiedName());
				}
			}*/
			
			// Class and Package
			ITypeBinding clazz = methodBinding.getDeclaringClass();
			if(clazz != null) {
				method.setClazz(clazz.getName());
				
				IPackageBinding pkg = clazz.getPackage();
				if(pkg != null)
					method.setPkg(pkg.getName());
			}
			
			return method;
		}
		else
			return null;
	}
}
