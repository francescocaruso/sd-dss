/*
 * DSS - Digital Signature Services
 *
 * Copyright (C) 2013 European Commission, Directorate-General Internal Market and Services (DG MARKT), B-1049 Bruxelles/Brussel
 *
 * Developed by: 2013 ARHS Developments S.A. (rue Nicolas Bové 2B, L-1253 Luxembourg) http://www.arhs-developments.com
 *
 * This file is part of the "DSS - Digital Signature Services" project.
 *
 * "DSS - Digital Signature Services" is free software: you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * DSS is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * "DSS - Digital Signature Services".  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.europa.ec.markt.dss.commons.swing.mvc.applet.wizard;

import java.util.Map;

import javax.swing.*;

import com.jgoodies.binding.beans.Model;
import eu.europa.ec.markt.dss.applet.controller.DSSAppletController;
import eu.europa.ec.markt.dss.applet.main.DSSAppletCore;
import eu.europa.ec.markt.dss.applet.view.WaitingGlassPanel;

/**
 * 
 * TODO
 * 
 * <p>
 * DISCLAIMER: Project owner DG-MARKT.
 * 
 * @version $Revision: 1016 $ - $Date: 2011-06-17 15:30:45 +0200 (Fri, 17 Jun 2011) $
 * @author <a href="mailto:dgmarkt.Project-DSS@arhs-developments.com">ARHS Developments</a>
 * @param <M>
 */
public abstract class WizardController<M extends Model> extends DSSAppletController<M> {

    private final Map<Class<? extends WizardStep<M, ? extends WizardController<M>>>, ? extends WizardStep<M, ? extends WizardController<M>>> wizardSteps;

    private WizardStep<M, ? extends WizardController<M>> currentStep;

    private WizardView<M, ? extends WizardController<M>> currentView;

    private final int maxStep;

    /**
     * 
     * The default constructor for WizardController.
     * 
     * @param core
     * @param model
     */
    protected WizardController(final DSSAppletCore core, final M model) {
        super(core, model);

        registerViews();
        wizardSteps = registerWizardStep();
        if (wizardSteps.size() == 0) {
            throw new RuntimeException("Please register at least one step");
        }

        int max = 0;

        for (final WizardStep<M, ? extends WizardController<M>> step : wizardSteps.values()) {
            max = step.getStepProgression() > max ? step.getStepProgression() : max;
        }

        maxStep = max;

    }

    /**
     * 
     * @throws Exception
     */
    public void doBack() {
        if (!hasBack()) {
            return;
        }
        final WizardStep<M, ? extends WizardController<M>> step = wizardSteps.get(this.currentStep.getBackStep());
        execute(step);
    }

    /**
     * 
     */
    protected abstract void doCancel();

    /**
     * 
     * @throws Exception
     */
    public void doNext() {

        if (!hasNext()) {
            return;
        }

        final WizardStep<M, ? extends WizardController<M>> currentStep = this.currentStep;
        final WizardStep<M, ? extends WizardController<M>> nextStep = wizardSteps.get(this.currentStep.getNextStep());

        final SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>() {
            /*
             * (non-Javadoc)
             * 
             * @see javax.swing.SwingWorker#doInBackground()
             */
            @Override
            protected Object doInBackground() throws Exception {
                try {

                    // Finish current wizard step
                    LOG.info("Finish wizard step {}", new Object[] { currentStep });
                    final JPanel glassPanel = new WaitingGlassPanel();
                    getCore().setGlassPane(glassPanel);
                    getCore().getGlassPane().setVisible(true);
                    currentStep.finish();

                    // Init the next wizard step
                    LOG.info("Init wizard step {}", new Object[] { nextStep });
                    setCurrentWizardStep(nextStep);
                    currentView = nextStep.getView();
                    nextStep.init();
                    currentView.show();
                    getCore().getGlassPane().setVisible(false);

                    return null;
                } catch (final Exception e) {
                    LOG.error("Execute fail", e);
                    if (e.getMessage() == null) {
                        JOptionPane.showMessageDialog(getCore(), e);
                    } else {
                        JOptionPane.showMessageDialog(getCore(), e.getMessage());
                    }
                    doBack();
                    return null;
                }
            }

        };

        worker.execute();

    }

    /**
     * 
     * @return
     */
    protected abstract Class<? extends WizardStep<M, ? extends WizardController<M>>> doStart();

    /**
     * 
     * @param wizardStep
     * @throws Exception
     */
    private void execute(final WizardStep<M, ? extends WizardController<M>> wizardStep) {

        final SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>() {
            /*
             * (non-Javadoc)
             * 
             * @see javax.swing.SwingWorker#doInBackground()
             */
            @Override
            protected Object doInBackground() throws Exception {
                try {
                    LOG.info("Execute step {}", new Object[] { wizardStep });
                    final JPanel glassPanel = new WaitingGlassPanel();
                    getCore().setGlassPane(glassPanel);
                    getCore().getGlassPane().setVisible(true);
                    setCurrentWizardStep(wizardStep);
                    currentView = wizardStep.getView();
                    wizardStep.init();
                    currentView.show();
                    getCore().getGlassPane().setVisible(false);
                    return null;
                } catch (final Exception e) {
                    LOG.error("Execute fail", e);
                    JOptionPane.showMessageDialog(getCore(), e);
                    doBack();
                    return null;
                }
            }

        };

        worker.execute();
    }

    /**
     * 
     * @return
     */
    public int getStepNumber() {
        return this.currentStep.getStepProgression();
    }

    /**
     * 
     * @return
     */
    public int getStepTotals() {
        return maxStep;
    }

    /**
     * 
     * @return
     */
    public boolean hasBack() {
        final Class<?> stepClass = this.currentStep.getBackStep();
        return stepClass != null && wizardSteps.containsKey(stepClass);
    }

    /**
     * 
     * @return
     */
    public boolean hasNext() {
        final Class<?> stepClass = this.currentStep.getNextStep();
        return stepClass != null && wizardSteps.containsKey(stepClass);
    }

    /**
     * 
     * @return
     */
    public boolean isLast() {
        return (getStepTotals() - 1) == getStepNumber();
    }

    /**
     * 
     * @return
     */
    public boolean isValid() {
        return this.currentStep.isValid();
    }

    protected abstract void registerViews();

    /**
     * 
     * @return
     */
    protected abstract Map<Class<? extends WizardStep<M, ? extends WizardController<M>>>, ? extends WizardStep<M, ? extends WizardController<M>>> registerWizardStep();

    /**
     * 
     * @param wizardStep
     */
    private void setCurrentWizardStep(final WizardStep<M, ? extends WizardController<M>> wizardStep) {
        this.currentStep = wizardStep;
    }

    /**
     * /**
     * 
     * @throws Exception
     */
    public void start() {
        final WizardStep<M, ? extends WizardController<M>> step = wizardSteps.get(doStart());
        execute(step);
    }

}
