/*
 * Copyright ©2020 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2020 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package poly;

import java.util.Iterator;
import java.util.Stack;

/**
 * <b>RatPolyStack</B> is a mutable finite sequence of RatPoly objects.
 *
 * <p>Each RatPolyStack can be described by [p1, p2, ... ], where [] is an empty stack, [p1] is a
 * one element stack containing the Poly 'p1', and so on. RatPolyStacks can also be described
 * constructively, with the append operation, ':'. such that [p1]:S is the result of putting p1 at
 * the front of the RatPolyStack S.
 *
 * <p>A finite sequence has an associated size, corresponding to the number of elements in the
 * sequence. Thus the size of [] is 0, the size of [p1] is 1, the size of [p1, p1] is 2, and so on.
 */
@SuppressWarnings("JdkObsolete")
public final class RatPolyStack implements Iterable<RatPoly> {

    /**
     * Stack containing the RatPoly objects.
     */
    private final Stack<RatPoly> polys;

    // Abstraction Function:
    // Each element of a RatPolyStack, s, is mapped to the
    // corresponding element of polys.
    //
    // RepInvariant:
    // polys != null &&
    // forall i such that (0 <= i < polys.size(), polys.get(i) != null

    /**
     * @spec.effects Constructs a new RatPolyStack, [].
     */
    public RatPolyStack() {
        polys = new Stack<RatPoly>();
        checkRep();
    }

    /**
     * Returns the number of RatPolys in this RatPolyStack.
     *
     * @return the size of this sequence
     */
    public int size() {
        // TODO: Fill in this method, then remove the RuntimeException
        return polys.size();
    }

    /**
     * Pushes a RatPoly onto the top of this.
     *
     * @param p the RatPoly to push onto this stack
     * @spec.requires p != null
     * @spec.modifies this
     * @spec.effects this_post = [p]:this
     */
    public void push(RatPoly p) {
        // TODO: Fill in this method, then remove the RuntimeException
        this.polys.push(p);
    }

    /**
     * Removes and returns the top RatPoly.
     *
     * @return p where this = [p]:S
     * @spec.requires {@code this.size() > 0}
     * @spec.modifies this
     * @spec.effects If this = [p]:S then this_post = S
     */
    public RatPoly pop() {
        // TODO: Fill in this method, then remove the RuntimeException

        return this.polys.pop();
    }

    /**
     * Duplicates the top RatPoly on this.
     *
     * @spec.requires {@code this.size() > 0}
     * @spec.modifies this
     * @spec.effects If this = [p]:S then this_post = [p, p]:S
     */
    public void dup() {
        // TODO: Fill in this method, then remove the RuntimeException
        RatPoly top = this.pop();
        this.push(top);
        this.push(top);
    }

    /**
     * Swaps the top two elements of this.
     *
     * @spec.requires {@code this.size() >= 2}
     * @spec.modifies this
     * @spec.effects If this = [p1, p2]:S then this_post = [p2, p1]:S
     */
    public void swap() {
        // TODO: Fill in this method, then remove the RuntimeException
        RatPoly top = this.pop();
        RatPoly sec = this.pop();
        this.push(top);
        this.push(sec);
    }

    /**
     * Clears the stack.
     *
     * @spec.modifies this
     * @spec.effects this_post = []
     */
    public void clear() {
        // TODO: Fill in this method, then remove the RuntimeException
        this.polys.clear();
    }

    /**
     * Returns the RatPoly that is 'index' elements from the top of the stack.
     *
     * @param index the index of the RatPoly to be retrieved
     * @return if this = S:[p]:T where S.size() = index, then returns p.
     * @spec.requires {@code index >= 0 && index < this.size()}
     */
    public RatPoly getNthFromTop(int index) {
        // TODO: Fill in this method, then remove the RuntimeException
        Stack<RatPoly> temp = new Stack<>();
        int i = 0;
        while(i < index){
            temp.push(this.pop());
            i = i + 1;
        }
        RatPoly res = polys.peek();
        while(!temp.empty()){
            polys.push(temp.pop());
        }
        return res;
    }

    /**
     * Pops two elements off of the stack, adds them, and places the result on top of the stack.
     *
     * @spec.requires {@code this.size() >= 2}
     * @spec.modifies this
     * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p1 + p2
     */
    public void add() {
        // TODO: Fill in this method, then remove the RuntimeException
        RatPoly p1 = this.pop();
        RatPoly p2 = this.pop();
        this.push(p1.add(p2));
    }

    /**
     * Subtracts the top poly from the next from top poly, pops both off the stack, and places the
     * result on top of the stack.
     *
     * @spec.requires {@code this.size() >= 2}
     * @spec.modifies this
     * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p2 - p1
     */
    public void sub() {
        // TODO: Fill in this method, then remove the RuntimeException
        RatPoly p1 = this.pop();
        RatPoly p2 = this.pop();
        this.push(p2.sub(p1));
    }

    /**
     * Pops two elements off of the stack, multiplies them, and places the result on top of the stack.
     *
     * @spec.requires {@code this.size() >= 2}
     * @spec.modifies this
     * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p1 * p2
     */
    public void mul() {
        // TODO: Fill in this method, then remove the RuntimeException
        RatPoly p1 = this.pop();
        RatPoly p2 = this.pop();
        this.push(p2.mul(p1));
    }

    /**
     * Divides the next from top poly by the top poly, pops both off the stack, and places the result
     * on top of the stack.
     *
     * @spec.requires {@code this.size() >= 2}
     * @spec.modifies this
     * @spec.effects If this = [p1, p2]:S then this_post = [p3]:S where p3 = p2 / p1
     */
    public void div() {
        // TODO: Fill in this method, then remove the RuntimeException
        RatPoly p1 = this.pop();
        RatPoly p2 = this.pop();
        this.push(p2.div(p1));
    }

    /**
     * Pops the top element off of the stack, differentiates it, and places the result on top of the
     * stack.
     *
     * @spec.requires {@code this.size() >= 1}
     * @spec.modifies this
     * @spec.effects If this = [p1]:S then this_post = [p2]:S where p2 = derivative of p1
     */
    public void differentiate() {
        // TODO: Fill in this method, then remove the RuntimeException
        this.push(this.pop().differentiate());
    }

    /**
     * Pops the top element off of the stack, integrates it, and places the result on top of the
     * stack.
     *
     * @spec.requires {@code this.size() >= 1}
     * @spec.modifies this
     * @spec.effects If this = [p1]:S then this_post = [p2]:S where p2 = indefinite integral of p1
     * with integration constant 0
     */
    public void integrate() {
        // TODO: Fill in this method, then remove the RuntimeException
        this.push(this.pop().antiDifferentiate(RatNum.ZERO));
    }

    /**
     * Returns an iterator of the elements contained in the stack.
     *
     * @return an iterator of the elements contained in the stack in order from the bottom of the
     * stack to the top of the stack
     */
    @Override
    public Iterator<RatPoly> iterator() {
        return polys.iterator();
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        assert (polys != null) : "polys should never be null.";

        for(RatPoly p : polys) {
            assert (p != null) : "polys should never contain a null element.";
        }
    }
}
